/*
 * AttributeTypesExpression
 *
 * $Revision$ $Date$
 * $Author$
 *
 * Copyright (c) 2013 NoMagic, Inc. All Rights Reserved.
 */
package com.nomagic.cameo.ontology.derivedproperties.expressions.element;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.nomagic.magicdraw.validation.SmartListenerConfigurationProvider;
import com.nomagic.uml2.ext.jmi.reflect.Expression;
import com.nomagic.uml2.ext.jmi.smartlistener.SmartListenerConfig;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Class;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Element;
import com.nomagic.uml2.impl.PropertyNames;

import javax.annotation.CheckForNull;
import javax.jmi.reflect.RefObject;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Expression collects and returns a collection of Self objects for
 * the given Element.
 *
 * @author Lonnie VanZandt
 * @version 1.0
 */
public class SelfExpression implements Expression, SmartListenerConfigurationProvider
{
    /**
     * Returns empty collection if the specified object is not a Element.
     * If the specified object is Element then it returns a singleton collection
     * with that element.
     *
     * @param object the context Element from the current MD model.
     * @return collection of Self.
     */
    @Override
    public Object getValue (@CheckForNull RefObject object)
    {
        List<Object> values = Lists.newArrayList();

        if (object instanceof Element)
        {
            values.add(object);
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

        // if the name of the element changes
        smartListenerCfg.listenTo(PropertyNames.NAME);

        listeners.add(smartListenerCfg);

        configs.put(Class.class, listeners);

        return configs;
    }
}
