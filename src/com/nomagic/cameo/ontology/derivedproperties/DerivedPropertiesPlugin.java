/*
 * DerivedPropertiesPlugin
 *
 * $Revision$ $Date$
 * $Author$
 *
 * Copyright (c) 2013 NoMagic, Inc. All Rights Reserved.
 */
package com.nomagic.cameo.ontology.derivedproperties;

import com.google.common.base.Optional;
import com.nomagic.cameo.ontology.derivedproperties.diagrams.owl.business.OwlBusinessDiagram;
import com.nomagic.magicdraw.core.Application;
import com.nomagic.magicdraw.evaluation.EvaluationConfigurator;
import com.nomagic.magicdraw.plugins.Plugin;
import com.nomagic.magicdraw.uml.DiagramDescriptor;
import com.nomagic.magicdraw.utils.MDLog;
import org.apache.log4j.Logger;

/**
 * This plugin registers the classes for the Derived Properties of Ontology modeling.
 *
 * @author Lonnie VanZandt
 */
public class DerivedPropertiesPlugin extends Plugin
{
    public final static String DERIVED_PROPERTIES_PLUGIN_ID = "com.nomagic.cameo.ontology.derivedproperties";

    @Override
    public boolean isSupported()
    {
        return true;
    }

    @Override
    public void init()
    {
        Logger pluginLogger = MDLog.getPluginsLog();

        Optional<DiagramDescriptor> owlDiagram = Optional.fromNullable(
                Application.getInstance().getDiagramDescriptor(OwlBusinessDiagram.OWL_BUSINESS_DIAGRAM_ID));

        if (!owlDiagram.isPresent()) {
            String missingDiagram = "Functionality of the Ontology Derived Properties will be limited due to lack of the expected "
                    + OwlBusinessDiagram.OWL_BUSINESS_DIAGRAM_ID + " diagram. Please investigate the adequacy of the "
                    + "software installations and contact Customer Support as necessary.";

            MDLog.getPluginsLog().warn(missingDiagram);
        }

        // Called only after isSupported when isSupported returns true
        EvaluationConfigurator.getInstance().registerBinaryImplementers(DerivedPropertiesPlugin.class.getClassLoader());

        pluginLogger.info("Derived Properties Ontology Plugin. Compiled at @@@compile-timestamp@@@ (debug=@@@debug-flag@@@).");
    }

    @Override
    public boolean close()
    {
        return true;
    }

}
