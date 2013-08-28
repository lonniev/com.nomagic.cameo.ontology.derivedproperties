package com.nomagic.cameo.partners.omg.fibo.plugins.derivedproperties.expressions;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.nomagic.uml2.ext.jmi.smartlistener.SmartListenerConfig;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Class;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Element;
import com.nomagic.uml2.impl.PropertyNames;

import java.util.Collection;
import java.util.Map;

public class SlotNameAndValueListenerConfigFactory
{
    static public Map<java.lang.Class<? extends Element>, Collection<SmartListenerConfig>> getListenerConfigurations ()
    {
        Map<java.lang.Class<? extends Element>, Collection<SmartListenerConfig>> configs = Maps.newHashMap();

        Collection<SmartListenerConfig> listeners = Lists.newArrayList();
        SmartListenerConfig smartListenerCfg = new SmartListenerConfig();

        // if the Slot Names or Slot Values of the association change
        smartListenerCfg.listenTo(PropertyNames.SLOT);

        listeners.add(smartListenerCfg);

        smartListenerCfg.listenToNested(PropertyNames.SLOT).listenTo(PropertyNames.VALUE);

        listeners.add(smartListenerCfg);

        configs.put(Class.class, listeners);

        return configs;
    }
}