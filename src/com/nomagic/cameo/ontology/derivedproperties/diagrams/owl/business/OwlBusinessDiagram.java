package com.nomagic.cameo.ontology.derivedproperties.diagrams.owl.business;

import com.nomagic.actions.AMConfigurator;
import com.nomagic.magicdraw.actions.ActionsCreator;
import com.nomagic.magicdraw.actions.ActionsProvider;
import com.nomagic.magicdraw.actions.DiagramContextAMConfigurator;
import com.nomagic.magicdraw.actions.MDActionsManager;
import com.nomagic.magicdraw.ui.ImageIconProxy;
import com.nomagic.magicdraw.ui.ResizableIconHelper;
import com.nomagic.magicdraw.ui.VectorImageIconControler;
import com.nomagic.magicdraw.ui.actions.BaseDiagramContextAMConfigurator;
import com.nomagic.magicdraw.ui.actions.ClassDiagramShortcutsConfigurator;
import com.nomagic.magicdraw.uml.DiagramDescriptor;
import com.nomagic.magicdraw.uml.DiagramType;
import com.nomagic.ui.ResizableIcon;

import javax.swing.*;
import java.net.URL;

/**
 * Created with IntelliJ IDEA.
 * User: lvanzandt
 * Date: 8/13/13
 * Time: 7:08 PM
 * To change this template use File | Settings | File Templates.
 */
public class OwlBusinessDiagram extends DiagramDescriptor
{
    private final static String OwlBusinessDiagramName = "OWL Business Diagram";
    private final static String OwlBusinessDiagramCollectionName = OwlBusinessDiagramName + "s";
    private MDActionsManager mOwlBusinessDiagramToolbarsActions;
    public final static String OWL_BUSINESS_DIAGRAM_ID = "com.nomagic.cameo.ontology.derivedproperties.diagrams.owl.business";

    @Override
    public String getSuperType()
    {
        return DiagramType.UML_CLASS_DIAGRAM;
    }

    @Override
    public boolean isCreatable()
    {
        return true;
    }

    @Override
    public AMConfigurator getDiagramToolbarConfigurator()
    {
        return new OwlBusinessDiagramToolbarConfigurator(getSuperType());
    }

    @Override
    public AMConfigurator getDiagramShortcutsConfigurator()
    {
        return new ClassDiagramShortcutsConfigurator();
    }

    @Override
    public DiagramContextAMConfigurator getDiagramContextConfigurator()
    {
        return new BaseDiagramContextAMConfigurator();
    }

    @Override
    public String getDiagramTypeId()
    {
        return OWL_BUSINESS_DIAGRAM_ID;
    }

    @Override
    public String getSingularDiagramTypeHumanName()
    {
        return OwlBusinessDiagramName;
    }

    @Override
    public String getPluralDiagramTypeHumanName()
    {
        return OwlBusinessDiagramCollectionName;
    }

    private URL getSVGIconURL()
    {
        return getClass().getResource("icons/owlBusinessDiagram.svg");
    }

    @Override
    public ImageIcon getLargeIcon()
    {
        return new ImageIconProxy(new VectorImageIconControler(getClass(),
                "icons/owlBusinessDiagram.svg", 16, VectorImageIconControler.SVG));
    }

    @Override
    public ResizableIcon getSVGIcon()
    {
        return ResizableIconHelper.createSVGIcon(getSVGIconURL());
    }

    @Override
    public URL getSmallIconURL()
    {
        return getSVGIconURL();
    }

    @Override
    public synchronized MDActionsManager getDiagramActions()
    {
        // if the Diagram Actions for this custom diagram type are not yet instantiated
        if (null == mOwlBusinessDiagramToolbarsActions) {
            // clone the ActionsManager that the UML Class Diagram has because it does everything we need
            ActionsCreator creator = ActionsProvider.getInstance().getCreator();
            mOwlBusinessDiagramToolbarsActions = (MDActionsManager) creator.createClassDiagramActions().clone();
        }

        return mOwlBusinessDiagramToolbarsActions;
    }
}
