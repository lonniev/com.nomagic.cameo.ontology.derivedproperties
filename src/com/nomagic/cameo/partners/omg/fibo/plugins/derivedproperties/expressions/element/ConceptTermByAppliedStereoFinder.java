package com.nomagic.cameo.partners.omg.fibo.plugins.derivedproperties.expressions.element;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableMap;
import com.nomagic.magicdraw.core.Application;
import com.nomagic.magicdraw.uml.ElementFinder;
import com.nomagic.uml2.ext.jmi.helpers.StereotypesHelper;
import com.nomagic.uml2.ext.magicdraw.auxiliaryconstructs.mdmodels.Model;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Element;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.LiteralString;

public class ConceptTermByAppliedStereoFinder
{
    private static final ImmutableMap<String, String> ConceptTerms = new ImmutableMap.Builder<String,
            String>()
                .put("owlClass", "Concept Type Owl Class")
                .put("objectProperty", "Concept Type Owl Object Property")
                .put("datatypeProperty", "Concept Type Owl Datatype Property")
                .put("annotationProperty", "Concept Type Owl Annotation Property")
                .put("unionOf", "Concept Type Owl UnionOf")
                .put("disjoint", "Concept Type Owl Disjoint")
                .put("UnionClass", "Concept Type Owl UnionClass")
                .put("owlRestriction", "Concept Type Owl Restriction").build();

    private static final boolean FindRecursively = true;

    public static Optional<LiteralString> findConceptType (Optional<? extends Element> element)
    {
        Optional<String> literalStringName = Optional.absent();

        // lookup from the map the name of the model literal that stores the user-name for the concept type
        for (String stereoNameKey : ConceptTerms.keySet())
        {
            if (StereotypesHelper.hasStereotype(element.get(), stereoNameKey))
            {
                literalStringName = Optional.of(ConceptTerms.get(stereoNameKey));
                break;
            }
        }

        Optional<Model> root = Optional.fromNullable(Application.getInstance().getProject().getModel());

        if (root.isPresent())
        {
            return Optional.fromNullable((LiteralString) ElementFinder.find(root.get(), LiteralString.class,
                    literalStringName.or("Concept Type Owl Other"), FindRecursively));
        }

        return Optional.absent();
    }
}