package com.nomagic.cameo.ontology.derivedproperties.expressions;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.nomagic.uml2.ext.jmi.smartlistener.SmartListenerConfig;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Class;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Element;
import com.nomagic.uml2.impl.PropertyNames;

import java.util.Collection;
import java.util.Map;

public class DependentNamedSupplierListenerConfigFactory
{
    static public Map<java.lang.Class<? extends Element>, Collection<SmartListenerConfig>> getListenerConfigurations()
    {

        Map<java.lang.Class<? extends Element>, Collection<SmartListenerConfig>> configs =
                Maps.newHashMap();

        Collection<SmartListenerConfig> listeners = Lists.newArrayList();
        SmartListenerConfig smartListenerCfg = new SmartListenerConfig();

        // if the client dependency changes its name
        smartListenerCfg.listenToNested(PropertyNames.SUPPLIER_DEPENDENCY)
                .listenTo(PropertyNames.NAME);

        // if the set of client dependencies changes
        smartListenerCfg.listenTo(PropertyNames.SUPPLIER_DEPENDENCY);

        listeners.add(smartListenerCfg);

        configs.put(
                Class.class,
                listeners);

        return configs;
    }
}