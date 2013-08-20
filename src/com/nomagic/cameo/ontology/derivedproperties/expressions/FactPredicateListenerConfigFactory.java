package com.nomagic.cameo.ontology.derivedproperties.expressions;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.nomagic.uml2.ext.jmi.smartlistener.SmartListenerConfig;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Class;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Element;
import com.nomagic.uml2.impl.PropertyNames;

import java.util.Collection;
import java.util.Map;

public class FactPredicateListenerConfigFactory
{
    static public Map<java.lang.Class<? extends Element>, Collection<SmartListenerConfig>> getListenerConfigurations ()
    {
        Map<java.lang.Class<? extends Element>, Collection<SmartListenerConfig>> configs = Maps.newHashMap();

        Collection<SmartListenerConfig> listeners = Lists.newArrayList();
        SmartListenerConfig smartListenerCfg = new SmartListenerConfig();

        // if the supplier at the end of the fact-dependency, predicate-dependency changes its name
        smartListenerCfg.listenToNested(PropertyNames.CLIENT_DEPENDENCY).listenTo(PropertyNames.CLIENT_DEPENDENCY).listenTo(PropertyNames.SUPPLIER).listenTo(PropertyNames.NAME);

        listeners.add(smartListenerCfg);

        // if the supplier at the end of the fact-dependency, predicate-dependency changes its type
        smartListenerCfg.listenToNested(PropertyNames.CLIENT_DEPENDENCY).listenTo(PropertyNames.CLIENT_DEPENDENCY).listenTo(PropertyNames.SUPPLIER).listenTo(PropertyNames.TYPE);

        listeners.add(smartListenerCfg);

        // if the supplier at the end of the fact-dependency changes its Specification
        smartListenerCfg.listenToNested(PropertyNames.CLIENT_DEPENDENCY).listenTo(PropertyNames.CLIENT_DEPENDENCY).listenTo(PropertyNames.SUPPLIER).listenTo(PropertyNames.SPECIFICATION);

        listeners.add(smartListenerCfg);

        // if the client dependency at the end of the fact-dependency changes its name
        smartListenerCfg.listenToNested(PropertyNames.CLIENT_DEPENDENCY).listenTo(PropertyNames.CLIENT_DEPENDENCY).listenTo(PropertyNames.NAME);

        listeners.add(smartListenerCfg);

        // if the client dependency changes its name
        smartListenerCfg.listenToNested(PropertyNames.CLIENT_DEPENDENCY).listenTo(PropertyNames.NAME);

        // if the set of client dependencies changes
        smartListenerCfg.listenTo(PropertyNames.CLIENT_DEPENDENCY);

        listeners.add(smartListenerCfg);

        configs.put(Class.class, listeners);

        return configs;
    }
}