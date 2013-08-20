package com.nomagic.cameo.ontology.derivedproperties.expressions;

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.nomagic.uml2.ext.jmi.helpers.StereotypesHelper;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Classifier;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.DirectedRelationship;

import javax.annotation.CheckForNull;
import java.util.List;

public class StereotypedRelationByNameFinder<T extends Classifier>
{
    private final T contextElement;

    public StereotypedRelationByNameFinder (@CheckForNull T contextElement)
    {
        this.contextElement = contextElement;
    }

    ;

    public static Predicate<DirectedRelationship> hasAppliedStereotypeWithNameOf (final String name)
    {
        return new HasAppliedStereotypeWithNameOf(name);
    }

    /**
     * finds the set of Relations with Applied Stereotype equal to the key for the
     * context Class.
     *
     * @param key       the name of the sought stereotype for the relationships
     * @param direction which sets of directed relationships to consider
     * @return collection of stereotyped relationships.
     */

    public List<DirectedRelationship> findRelationshipWithAppliedStereotypeName (String key, RelationshipDirection direction)
    {
        List<DirectedRelationship> values = Lists.newArrayList();

        // find the relationships for the context Class
        ImmutableList<DirectedRelationship> awayRelations = ImmutableList.of();
        ImmutableList<DirectedRelationship> towardsRelations = ImmutableList.of();

        if (RelationshipDirection.AwayFrom != direction)
        {
            awayRelations = ImmutableList.copyOf(contextElement.get_directedRelationshipOfTarget());
        }

        if (RelationshipDirection.Towards != direction)
        {
            towardsRelations = ImmutableList.copyOf(contextElement.get_directedRelationshipOfSource());
        }

        ImmutableList<DirectedRelationship> candidateRelations = new ImmutableList.Builder<DirectedRelationship>().addAll(awayRelations).addAll(towardsRelations).build();

        // select only those that have the applied Stereotype <key> name
        ImmutableList<DirectedRelationship> matchingRelation = ImmutableList.copyOf(Collections2.filter(candidateRelations, hasAppliedStereotypeWithNameOf(key)));

        values.addAll(matchingRelation);

        return values;
    }

    public enum RelationshipDirection
    {
        AwayFrom, Towards, Both
    }

    private static class HasAppliedStereotypeWithNameOf implements Predicate<DirectedRelationship>
    {
        private final String name;

        private HasAppliedStereotypeWithNameOf (final String name)
        {
            this.name = name;
        }

        public boolean apply (final DirectedRelationship dr)
        {
            return (null != dr) && StereotypesHelper.isElementStereotypedBy(dr, name);
        }
    }
}