package com.nomagic.cameo.partners.omg.fibo.plugins.derivedproperties.expressions.owlclass;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.nomagic.cameo.partners.omg.fibo.plugins.derivedproperties.expressions.StereotypedElement;
import com.nomagic.cameo.partners.omg.fibo.plugins.derivedproperties.expressions.StereotypedRelationByNameFinder;
import com.nomagic.magicdraw.validation.SmartListenerConfigurationProvider;
import com.nomagic.uml2.ext.jmi.reflect.Expression;
import com.nomagic.uml2.ext.jmi.smartlistener.SmartListenerConfig;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Class;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.DirectedRelationship;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Element;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.ValueSpecification;
import com.nomagic.uml2.impl.PropertyNames;

import javax.annotation.CheckForNull;
import javax.jmi.reflect.RefObject;
import java.util.Collection;
import java.util.List;
import java.util.Map;


/**
 * User: lvanzandt
 * Date: 7/24/13
 * Time: 8:59 AM
 */
public class InversePropertyLabelExpression implements Expression, SmartListenerConfigurationProvider
{
    /**
     * Returns an empty collection if the specified object is not an OWL Class. If
     * the specified object is an OWL Class then it returns the set of Types in the Range of
     * the Property.
     *
     * @param object the context Element from the current MD model.
     * @return collection of [0..1] related OWL Class Types of the Range.
     */
    @Override
    public Object getValue (@CheckForNull RefObject object)
    {
        List<ValueSpecification> values = Lists.newArrayList();

        if (object instanceof Class)
        {
            final Class owlClass = (Class) object;

            // look for a relationship away from this element with the stereotype of inverseOf
            StereotypedRelationByNameFinder<Class> relationFinder = new StereotypedRelationByNameFinder
                    (owlClass);

            ImmutableList<DirectedRelationship> inverseOfRelations = ImmutableList.copyOf(relationFinder
                    .findRelationshipWithAppliedStereotypeName("inverseOf", StereotypedRelationByNameFinder
                            .RelationshipDirection.AwayFrom));

            // for each such inverseOf relationship (of which there should be only at most one)
            for (DirectedRelationship inverseRel : inverseOfRelations)
            {
                // for each of the targets of this inverse relationship
                for (Element target : inverseRel.getTarget())
                {
                    if (target instanceof Class)
                    {
                        final Class inverseClass = (Class) target;

                        final StereotypedElement<Class> inverseowlClass = new
                                StereotypedElement<Class>(inverseClass, "labeledResource");

                        // add all the labels this labeled resource may have
                        values.addAll(inverseowlClass.getTagValueValueSpecificationByName("label"));
                    }
                }
            }
        }

        return values;
    }

    /**
     * {@inheritDoc} An implementation of the
     * com.nomagic.magicdraw.validation.SmartListenerConfigurationProvider
     * interface has to return the configuration that identifies the set of
     * properties that are important to the expression. The derived property
     * will be recalculated when one or more of these properties change.
     */
    @Override
    public Map<java.lang.Class<? extends Element>, Collection<SmartListenerConfig>> getListenerConfigurations ()
    {
        Map<java.lang.Class<? extends Element>, Collection<SmartListenerConfig>> configs = Maps.newHashMap();

        Collection<SmartListenerConfig> listeners = Lists.newArrayList();
        SmartListenerConfig smartListenerCfg = new SmartListenerConfig();

        // if the Roles of the Class change
        smartListenerCfg.listenTo(PropertyNames.OUTGOING);

        listeners.add(smartListenerCfg);

        configs.put(Class.class, listeners);

        return configs;
    }
}
