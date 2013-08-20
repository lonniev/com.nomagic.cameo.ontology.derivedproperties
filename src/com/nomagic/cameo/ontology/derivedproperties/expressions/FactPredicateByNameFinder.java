package com.nomagic.cameo.ontology.derivedproperties.expressions;

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.nomagic.uml2.ext.jmi.helpers.ModelHelper;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Classifier;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.DirectedRelationship;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Element;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.InstanceSpecification;

import javax.annotation.CheckForNull;
import java.util.List;

public class FactPredicateByNameFinder
{
    private final Classifier contextClassifier;

    public FactPredicateByNameFinder (@CheckForNull Classifier contextClassifier)
    {
        this.contextClassifier = contextClassifier;
    }

    public static Predicate<DirectedRelationship> hasNameOf (final String name)
    {
        return new HasNameOf(name);
    }

    public static Predicate<DirectedRelationship> hasSupplierWithNameOf (final String name)
    {
        return new HasSupplierWithNameOf(name);
    }

    /**
     * finds the set of InstanceSpecifications with name equal to the key for the
     * context Association (range is OwlClass, ObjectProperty).
     *
     * @param key the name of the InstanceSpecifications sought.
     * @return collection of related InstanceSpecifications.
     */

    public List<InstanceSpecification> findInstanceSpecifications (String key)
    {

        List<InstanceSpecification> values = Lists.newArrayList();

        // find the relationships for the context Class

        ImmutableList<DirectedRelationship> fromRelations = ImmutableList.copyOf(contextClassifier
                .get_directedRelationshipOfSource());

        // select only those that have the name "fact"
        ImmutableList<DirectedRelationship> factRelations = ImmutableList.copyOf(Collections2.filter(fromRelations,
                hasNameOf("fact")));

        // for each "fact" relationship
        for (DirectedRelationship factRel : factRelations)
        {

            // find the relationships of the "fact"
            ImmutableList<DirectedRelationship> fromFactRelRelations = ImmutableList.copyOf(factRel
                    .get_directedRelationshipOfSource());

            // select only those that have the name "predicate"
            ImmutableList<DirectedRelationship> predicateRelations = ImmutableList.copyOf(Collections2.filter
                    (fromFactRelRelations, hasNameOf("predicate")));

            // select only those predicate relationships that have a
            // supplier named "<key>"
            ImmutableList<DirectedRelationship> predRelsToDefinitions = ImmutableList.copyOf(Collections2.filter
                    (predicateRelations, hasSupplierWithNameOf(key)));

            // if this factRel has at least one predicate relationship to a
            // key-named InstanceSpecification
            if (0 < predRelsToDefinitions.size())
            {
                // then, in fact, this factRel points to a
                // InstanceSpecification that holds
                // the SKOS definition for the class
                values.add((InstanceSpecification) ModelHelper.getSupplierElement(factRel));
            }
        }

        return values;
    }

    private static class HasNameOf implements Predicate<DirectedRelationship>
    {
        private final String name;

        private HasNameOf (final String name)
        {
            this.name = name;
        }

        public boolean apply (final DirectedRelationship dr)
        {
            return dr.getHumanName().startsWith(name);
        }
    }

    private static class HasSupplierWithNameOf implements Predicate<DirectedRelationship>
    {
        private final String name;

        private HasSupplierWithNameOf (final String name)
        {
            this.name = name;
        }

        public boolean apply (final DirectedRelationship dr)
        {

            Element supplier = ModelHelper.getSupplierElement(dr);

            return (null != supplier) && supplier.getHumanName().contains(name);
        }
    }
}