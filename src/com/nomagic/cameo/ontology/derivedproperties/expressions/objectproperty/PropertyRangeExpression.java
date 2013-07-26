package com.nomagic.cameo.ontology.derivedproperties.expressions.objectproperty;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.nomagic.magicdraw.validation.SmartListenerConfigurationProvider;
import com.nomagic.uml2.ext.jmi.reflect.Expression;
import com.nomagic.uml2.ext.jmi.smartlistener.SmartListenerConfig;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.*;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Class;
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
public class PropertyRangeExpression implements Expression,
        SmartListenerConfigurationProvider
{
    /**
     * Returns an empty collection if the specified object is not an OWL objectProperty Association. If
     * the specified object is an OWL objectProperty then it returns the set of Types in the Range of
     * the objectProperty.
     *
     * @param object the context Element from the current MD model.
     * @return collection of [0..1] related OWL Class Types of the Range.
     */
    @Override
    public Object getValue(@CheckForNull RefObject object)
    {
        List<Type> values = Lists.newArrayList();

        if (object instanceof Association) {

            Association objectProperty = (Association) object;

            // get the set of roles (MemberEnds) of this Association(Class)
            ImmutableList<Property> assocProperties = ImmutableList.copyOf(objectProperty.getMemberEnd());

            // a well-formed UML and ODM-compliant model should return a collection with only 2 members

            for (Property assocProperty : assocProperties) {

                // if the property can be reached from this objectProperty
                if (assocProperty.isNavigable()) {

                    // according to the ODM Specification, version 1, if the property is
                    // navigable from the owner then it references the range of the objectProperty
                    // (otherwise it references the domain thereof)

                    // then obtain its Type because that is the range of the objectProperty
                    Type rangeType = assocProperty.getType();

                    values.add(rangeType);
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
