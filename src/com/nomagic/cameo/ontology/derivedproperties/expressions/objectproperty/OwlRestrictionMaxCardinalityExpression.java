package com.nomagic.cameo.ontology.derivedproperties.expressions.objectproperty;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.nomagic.cameo.ontology.derivedproperties.expressions.MatchingRelationByNameFinder;
import com.nomagic.cameo.ontology.derivedproperties.expressions.StereotypedElement;
import com.nomagic.magicdraw.validation.SmartListenerConfigurationProvider;
import com.nomagic.uml2.ext.jmi.reflect.Expression;
import com.nomagic.uml2.ext.jmi.smartlistener.SmartListenerConfig;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Class;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Element;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.ValueSpecification;
import com.nomagic.uml2.impl.PropertyNames;

import javax.annotation.CheckForNull;
import javax.jmi.reflect.RefObject;
import java.util.Collection;
import java.util.List;
import java.util.Map;


/**
 * Created with IntelliJ IDEA.
 * User: lvanzandt
 * Date: 7/24/13
 * Time: 8:59 AM
 * To change this template use File | Settings | File Templates.
 */
public class OwlRestrictionMaxCardinalityExpression implements Expression,
        SmartListenerConfigurationProvider
{
    private final String stereoName = "owlRestriction";
    private final String propertyName = "maxCardinality";

    /**
     * Returns empty collection if the specified object is not an OWL objectProperty. If
     * specified object is an OWL objectProperty then returns the set of owlRestriction cardinalities.
     *
     * @param object the context Element from the current MD model.
     * @return collection of related OWL Restriction cardinalities.
     */
    @Override
    public Object getValue(@CheckForNull RefObject object)
    {
        List<ValueSpecification> values = Lists.newArrayList();

        if (object instanceof Class) {

            Class objectProperty = (Class) object;

            MatchingRelationByNameFinder matchingRelationByNameFinder =
                    new MatchingRelationByNameFinder(objectProperty);

            ImmutableList<Class> owlRestrictions = ImmutableList
                    .copyOf(matchingRelationByNameFinder.findRelatedClassWithAppliedStereotypeName(stereoName));

            for (Class owlRestrictClass : owlRestrictions) {

                StereotypedElement<Class> owlRestrict = new StereotypedElement<Class>(owlRestrictClass, stereoName);

                values.addAll(owlRestrict.getTagValueValueSpecificationByName(propertyName));
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
    public Map<java.lang.Class<? extends Element>, Collection<SmartListenerConfig>> getListenerConfigurations()
    {

        Map<java.lang.Class<? extends Element>, Collection<SmartListenerConfig>> configs =
                Maps.newHashMap();

        Collection<SmartListenerConfig> listeners = Lists.newArrayList();
        SmartListenerConfig smartListenerCfg = new SmartListenerConfig();

// if the supplier at the end of the fact-dependency, predicate-dependency changes its name
        smartListenerCfg.listenToNested(PropertyNames.CLIENT_DEPENDENCY)
                .listenTo(PropertyNames.CLIENT_DEPENDENCY)
                .listenTo(PropertyNames.SUPPLIER)
                .listenTo(PropertyNames.NAME);

        listeners.add(smartListenerCfg);

// if the supplier at the end of the fact-dependency, predicate-dependency changes its type
        smartListenerCfg.listenToNested(PropertyNames.CLIENT_DEPENDENCY)
                .listenTo(PropertyNames.CLIENT_DEPENDENCY)
                .listenTo(PropertyNames.SUPPLIER)
                .listenTo(PropertyNames.TYPE);

        listeners.add(smartListenerCfg);

// if the supplier at the end of the fact-dependency changes its Specification
        smartListenerCfg.listenToNested(PropertyNames.CLIENT_DEPENDENCY)
                .listenTo(PropertyNames.CLIENT_DEPENDENCY)
                .listenTo(PropertyNames.SUPPLIER)
                .listenTo(PropertyNames.SPECIFICATION);

        listeners.add(smartListenerCfg);

// if the client dependency at the end of the fact-dependency changes its name
        smartListenerCfg.listenToNested(PropertyNames.CLIENT_DEPENDENCY)
                .listenTo(PropertyNames.CLIENT_DEPENDENCY)
                .listenTo(PropertyNames.NAME);

        listeners.add(smartListenerCfg);

// if the client dependency changes its name
        smartListenerCfg.listenToNested(PropertyNames.CLIENT_DEPENDENCY)
                .listenTo(PropertyNames.NAME);

// if the set of client dependencies changes
        smartListenerCfg.listenTo(PropertyNames.CLIENT_DEPENDENCY);

        listeners.add(smartListenerCfg);

        configs.put(
                Class.class,
                listeners);

        return configs;
    }
}
