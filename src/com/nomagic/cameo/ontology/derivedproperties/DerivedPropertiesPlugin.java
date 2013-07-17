/*
 * DerivedPropertiesPlugin
 *
 * $Revision$ $Date$
 * $Author$
 *
 * Copyright (c) 2013 NoMagic, Inc. All Rights Reserved.
 */
package com.nomagic.cameo.ontology.derivedproperties;

import com.nomagic.magicdraw.evaluation.EvaluationConfigurator;
import com.nomagic.magicdraw.plugins.Plugin;

/**
 * This plugin registers the classes for the Derived Properties of Ontology modeling.
 *
 * @author
 */
public class DerivedPropertiesPlugin extends Plugin
{
    @Override
	public boolean isSupported()
    {
        return true;
    }
    
    @Override
	public void init()
    {
    	// Called only after isSupported when isSupported returns true  	
        EvaluationConfigurator.getInstance().registerBinaryImplementers(DerivedPropertiesPlugin.class.getClassLoader());
    }

    @Override
	public boolean close()
    {
        return true;
    }

}
