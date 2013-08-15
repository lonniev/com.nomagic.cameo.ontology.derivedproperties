package com.nomagic.cameo.ontology.derivedproperties.diagrams.owl.business;

import com.nomagic.actions.ActionsManager;
import com.nomagic.magicdraw.ui.actions.BaseDiagramToolbarConfigurator;

/**
 * Created with IntelliJ IDEA.
 * User: lvanzandt
 * Date: 8/14/13
 * Time: 10:12 AM
 */
public class OwlBusinessDiagramToolbarConfigurator extends BaseDiagramToolbarConfigurator
{
    private String mSupertypeName;

    public OwlBusinessDiagramToolbarConfigurator(String superTypeName)
    {
        this.mSupertypeName = superTypeName;
    }

    /**
     * Configure given manager.
     *
     * @param manager the manager for configuration.
     */
    @Override
    public void configure(ActionsManager manager)
    {
    }
}
