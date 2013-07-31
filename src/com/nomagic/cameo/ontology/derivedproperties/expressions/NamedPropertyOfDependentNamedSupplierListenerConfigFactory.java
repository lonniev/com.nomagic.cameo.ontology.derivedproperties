package com.nomagic.cameo.ontology.derivedproperties.expressions;

import com.nomagic.uml2.ext.jmi.smartlistener.SmartListenerConfig;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Class;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Element;
import com.nomagic.uml2.impl.PropertyNames;

import java.util.Collection;
import java.util.Map;

public class NamedPropertyOfDependentNamedSupplierListenerConfigFactory
{
    static public Map<java.lang.Class<? extends Element>, Collection<SmartListenerConfig>> getListenerConfigurations()
    {
        Map<java.lang.Class<? extends Element>, Collection<SmartListenerConfig>> configs =
                DependentNamedSupplierListenerConfigFactory.getListenerConfigurations();

        SmartListenerConfig smartListenerCfg = new SmartListenerConfig();

        // if the value of the Tag Value property changes
        smartListenerCfg.listenToNested(PropertyNames.SUPPLIER_DEPENDENCY)
                .listenTo(PropertyNames.SLOT)
                .listenTo(PropertyNames.VALUE);

        configs.get(Class.class).add(smartListenerCfg);

        return configs;
    }
}