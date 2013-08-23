/*
 * DerivedPropertiesPlugin
 *
 * $Revision$ $Date$
 * $Author$
 *
 * Copyright (c) 2013 NoMagic, Inc. All Rights Reserved.
 */
package com.nomagic.cameo.partners.omg.fibo.plugins.derivedproperties;

import com.nomagic.magicdraw.evaluation.EvaluationConfigurator;
import com.nomagic.magicdraw.plugins.Plugin;
import com.nomagic.magicdraw.utils.MDLog;
import org.apache.log4j.Logger;

/**
 * This plugin registers the classes for the Derived Properties of Ontology modeling.
 *
 * @author Lonnie VanZandt
 */
public class DerivedPropertiesPlugin extends Plugin
{
    public final static String DERIVED_PROPERTIES_PLUGIN_ID = "com.nomagic.cameo.partners.omg.fibo.plugins.com.nomagic.cameo.partners.omg.fibo.plugins.derivedproperties";

    @Override
    public boolean isSupported ()
    {
        return true;
    }

    @Override
    public void init ()
    {
        Logger pluginLogger = MDLog.getGUILog();

        // Called only after isSupported when isSupported returns true
        EvaluationConfigurator.getInstance().registerBinaryImplementers(DerivedPropertiesPlugin.class.getClassLoader());

        pluginLogger.info("Derived Properties Ontology Plugin. Compiled at @@@compile-timestamp@@@ (debug=@@@debug-flag@@@).");
    }

    @Override
    public boolean close ()
    {
        return true;
    }

}
