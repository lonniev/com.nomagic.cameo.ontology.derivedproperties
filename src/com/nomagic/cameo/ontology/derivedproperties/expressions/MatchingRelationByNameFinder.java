package com.nomagic.cameo.ontology.derivedproperties.expressions;

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.nomagic.uml2.ext.jmi.helpers.ModelHelper;
import com.nomagic.uml2.ext.jmi.helpers.StereotypesHelper;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Class;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.DirectedRelationship;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Element;

import javax.annotation.CheckForNull;
import java.util.List;

public class MatchingRelationByNameFinder
{
    private final Class contextClass;

    public MatchingRelationByNameFinder(@CheckForNull Class contextClass)
    {
        this.contextClass = contextClass;
    }

    public static Predicate<DirectedRelationship> hasAppliedStereotypeWithNameOf(
            final String name)
    {
        return new HasAppliedStereotypeWithNameOf(name);
    }

    /**
     * finds the set of Classes with Applied Stereotype equal to the key for the
     * context Class (range is OwlClass, ObjectProperty).
     *
     * @return collection of related OWL Restrictions.
     */

    public List<Class> findRelatedClassWithAppliedStereotypeName(String key)
    {
        List<Class> values = Lists.newArrayList();

        // find the relationships for the context Class

        ImmutableList<DirectedRelationship> fromRelations = ImmutableList
                .copyOf(contextClass.get_directedRelationshipOfTarget());

        // select only those that have the applied Stereotype <key> name
        ImmutableList<DirectedRelationship> matchingRelation = ImmutableList
                .copyOf(Collections2.filter(fromRelations,
                        hasAppliedStereotypeWithNameOf(key)));

        // for each relationship to a matching class
        for (DirectedRelationship matchRel : matchingRelation) {

            // the OWL Restriction for the relationship
            values.add((Class) ModelHelper
                    .getClientElement(matchRel));
        }

        return values;
    }

    private static class HasAppliedStereotypeWithNameOf implements
            Predicate<DirectedRelationship>
    {
        private final String name;

        private HasAppliedStereotypeWithNameOf(final String name)
        {
            this.name = name;
        }

        public boolean apply(final DirectedRelationship dr)
        {
            Element client = ModelHelper.getClientElement(dr);

            return (null != client) && StereotypesHelper.isElementStereotypedBy(client, name);
        }
    }
}