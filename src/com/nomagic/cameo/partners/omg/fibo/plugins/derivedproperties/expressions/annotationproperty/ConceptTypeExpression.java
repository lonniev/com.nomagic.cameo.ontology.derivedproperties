package com.nomagic.cameo.partners.omg.fibo.plugins.derivedproperties.expressions.annotationproperty;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.nomagic.magicdraw.core.Application;
import com.nomagic.magicdraw.uml.ElementFinder;
import com.nomagic.magicdraw.validation.SmartListenerConfigurationProvider;
import com.nomagic.uml2.ext.jmi.helpers.StereotypesHelper;
import com.nomagic.uml2.ext.jmi.reflect.Expression;
import com.nomagic.uml2.ext.jmi.smartlistener.SmartListenerConfig;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Association;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Element;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.LiteralString;
import com.nomagic.uml2.impl.PropertyNames;

import javax.annotation.CheckForNull;
import javax.jmi.reflect.RefObject;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * User: lvanzandt
 * Date: 8/19/13
 * Time: 3:56 PM
 */
public class ConceptTypeExpression implements Expression, SmartListenerConfigurationProvider
{
    private static final ImmutableMap<String, String> conceptTerms = ImmutableMap.of("owlClass",
            "Concept Type Owl Class", "objectProperty", "Concept Type Owl Object Property", "datatypeProperty",
            "Concept Type Owl Datatype Property", "unionOf", "Concept Type Owl UnionOf", "disjoint",
            "Concept Type Owl Disjoint");

    private static final boolean FindRecursively = true;

    /**
     * Returns empty collection if the specified object is not an AssociationClass.
     * If the specified object is an AssociationClass then it returns a collection
     * of the rdf Labels of the Parents for the element.
     *
     * @param object the context Element from the current MD model.
     * @return collection of Parent.
     */
    @Override
    public Object getValue (@CheckForNull RefObject object)
    {
        List<LiteralString> values = Lists.newArrayList();

        if (object instanceof Association)
        {
            final Association assoc = (Association) object;
            String literalStringName = "Concept Type Owl Other";

            for (String stereoNameKey : conceptTerms.keySet())
            {
                if (StereotypesHelper.hasStereotype(assoc, stereoNameKey))
                {
                    literalStringName = conceptTerms.get(stereoNameKey);
                    break;
                }
            }

            Element root = Application.getInstance().getProject().getModel();

            LiteralString conceptTypeLiteralString = (LiteralString) ElementFinder.find(root, LiteralString.class,
                    literalStringName, FindRecursively);

            values.add(conceptTypeLiteralString);
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
    public Map<Class<? extends Element>, Collection<SmartListenerConfig>> getListenerConfigurations ()
    {
        Map<Class<? extends Element>, Collection<SmartListenerConfig>> configs = Maps.newHashMap();

        Collection<SmartListenerConfig> listeners = Lists.newArrayList();
        SmartListenerConfig smartListenerCfg = new SmartListenerConfig();

        // if the name of the element changes
        smartListenerCfg.listenTo(PropertyNames.NAME);

        listeners.add(smartListenerCfg);

        configs.put(com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Class.class, listeners);

        return configs;
    }
}
