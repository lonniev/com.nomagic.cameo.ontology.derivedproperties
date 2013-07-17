/*
 * AttributeTypesExpression
 *
 * $Revision$ $Date$
 * $Author$
 *
 * Copyright (c) 2013 NoMagic, Inc. All Rights Reserved.
 */
package com.nomagic.cameo.ontology.derivedproperties.skosdefinition;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.annotation.CheckForNull;
import javax.jmi.reflect.RefObject;

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.nomagic.magicdraw.validation.SmartListenerConfigurationProvider;
import com.nomagic.uml2.ext.jmi.helpers.ModelHelper;
import com.nomagic.uml2.ext.jmi.reflect.Expression;
import com.nomagic.uml2.ext.jmi.smartlistener.SmartListenerConfig;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Class;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.DirectedRelationship;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Element;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.InstanceSpecification;
import com.nomagic.uml2.impl.PropertyNames;

/**
 * Expression collects and returns a collection of SKOS Definition Strings for
 * the OWL Class.
 * 
 * @author
 * @version 1.0
 */
public class SkosDefinitionExpression implements Expression,
		SmartListenerConfigurationProvider {

	private static class HasNameOf implements Predicate<DirectedRelationship> {
		private final String name;

		private HasNameOf(final String name) {
			this.name = name;
		}

		public boolean apply(final DirectedRelationship dr) {

			return dr.getHumanName().startsWith(name);
		}
	}

	private static Predicate<DirectedRelationship> hasNameOf(final String name) {
		return new HasNameOf(name);
	}

	private static class HasSupplierWithNameOf implements
			Predicate<DirectedRelationship> {

		private final String name;

		private HasSupplierWithNameOf(final String name) {
			this.name = name;
		}

		public boolean apply(final DirectedRelationship dr) {

			Element supplier = ModelHelper.getSupplierElement(dr);

			return (null == supplier) ? false : supplier.getHumanName()
					.contains(name);
		}
	}

	private static Predicate<DirectedRelationship> hasSupplierWithNameOf(
			final String name) {
		return new HasSupplierWithNameOf(name);
	}

	/**
	 * Returns empty collection if the specified object is not an OWL class. If
	 * specified object is an OWL class then returns the set of SKOS Definitions
	 * for that OWL Class.
	 * 
	 * @param object
	 *            the context Element from the current MD model.
	 * @return collection of related SKOS Definition InstanceSpecifications.
	 */
	@Override
	public Object getValue(@CheckForNull RefObject object) {

		List<InstanceSpecification> definitions = Lists.newArrayList();

		if (object instanceof com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Class) {

			// find the relationships for the object
			com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Class owlClass = (Class) object;

			ImmutableList<DirectedRelationship> fromRelations = ImmutableList
					.copyOf(owlClass.get_directedRelationshipOfSource());

			// select only those that have the name "fact"
			ImmutableList<DirectedRelationship> factRelations = ImmutableList
					.copyOf(Collections2.filter(fromRelations,
							hasNameOf("fact")));

			// for each "fact" relationship
			for (DirectedRelationship factRel : factRelations) {

				// find the relationships of the "fact"
				ImmutableList<DirectedRelationship> fromFactRelRelations = ImmutableList
						.copyOf(factRel.get_directedRelationshipOfSource());

				// select only those that have the name "predicate"
				ImmutableList<DirectedRelationship> predicateRelations = ImmutableList
						.copyOf(Collections2.filter(fromFactRelRelations,
								hasNameOf("predicate")));

				// select only those predicate relationships that have a
				// supplier named "definition"
				ImmutableList<DirectedRelationship> predRelsToDefinitions = ImmutableList
						.copyOf(Collections2.filter(predicateRelations,
								hasSupplierWithNameOf("definition")));

				// if this factRel has at least one predicate relationship to a
				// definition
				if (0 < predRelsToDefinitions.size()) {
					// then, in fact, this factRel points to a
					// InstanceSpecification that holds
					// the SKOS definition for the class
					definitions.add((InstanceSpecification) ModelHelper
							.getSupplierElement(factRel));
				}
			}
		}

		return definitions;
	}

	/**
	 * {@inheritDoc} An implementation of the
	 * com.nomagic.magicdraw.validation.SmartListenerConfigurationProvider
	 * interface has to return the configuration that identifies the set of
	 * properties that are important to the expression. The derived property
	 * will be recalculated when one or more of these properties change.
	 */
	@Override
	public Map<java.lang.Class<? extends Element>, Collection<SmartListenerConfig>> getListenerConfigurations() {

		Map<java.lang.Class<? extends Element>, Collection<SmartListenerConfig>> configs = 
				Maps.newHashMap();

		Collection<SmartListenerConfig> listeners = Lists.newArrayList();
		SmartListenerConfig smartListenerCfg = new SmartListenerConfig();

		// if the supplier at the end of the fact-dependency, predicate-dependency changes its name
		smartListenerCfg.listenToNested(PropertyNames.CLIENT_DEPENDENCY)
			.listenTo(PropertyNames.CLIENT_DEPENDENCY)
				.listenTo(PropertyNames.SUPPLIER)
					.listenTo(PropertyNames.NAME);
		
		listeners.add(smartListenerCfg);
		
		// if the supplier at the end of the fact-dependency, predicate-dependency changes its type
		smartListenerCfg.listenToNested(PropertyNames.CLIENT_DEPENDENCY)
			.listenTo(PropertyNames.CLIENT_DEPENDENCY)
				.listenTo(PropertyNames.SUPPLIER)
					.listenTo(PropertyNames.TYPE);

		listeners.add(smartListenerCfg);
		
		// if the supplier at the end of the fact-dependency changes its Specification
		smartListenerCfg.listenToNested(PropertyNames.CLIENT_DEPENDENCY)
			.listenTo(PropertyNames.CLIENT_DEPENDENCY)
				.listenTo(PropertyNames.SUPPLIER)
					.listenTo(PropertyNames.SPECIFICATION);

		listeners.add(smartListenerCfg);
		
		// if the client dependency at the end of the fact-dependency changes its name
		smartListenerCfg.listenToNested(PropertyNames.CLIENT_DEPENDENCY)
			.listenTo(PropertyNames.CLIENT_DEPENDENCY)
				.listenTo(PropertyNames.NAME);

		listeners.add(smartListenerCfg);
		
		// if the client dependency changes its name
		smartListenerCfg.listenToNested(PropertyNames.CLIENT_DEPENDENCY)
			.listenTo(PropertyNames.NAME);
		
		// if the set of client dependencies changes
		smartListenerCfg.listenTo(PropertyNames.CLIENT_DEPENDENCY);

		listeners.add(smartListenerCfg);
		
		configs.put(
				com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Class.class,
				listeners);

		return configs;
	}
}
