/*
 * AttributeTypesExpression
 *
 * $Revision$ $Date$
 * $Author$
 *
 * Copyright (c) 2013 NoMagic, Inc. All Rights Reserved.
 */
package com.nomagic.cameo.partners.omg.fibo.plugins.derivedproperties.expressions.owlclass;

import com.google.common.collect.Lists;
import com.nomagic.cameo.partners.omg.fibo.plugins.derivedproperties.expressions.FactPredicateByNameFinder;
import com.nomagic.cameo.partners.omg.fibo.plugins.derivedproperties.expressions.FactPredicateListenerConfigFactory;
import com.nomagic.magicdraw.validation.SmartListenerConfigurationProvider;
import com.nomagic.uml2.ext.jmi.reflect.Expression;
import com.nomagic.uml2.ext.jmi.smartlistener.SmartListenerConfig;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Class;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Element;

import javax.annotation.CheckForNull;
import javax.jmi.reflect.RefObject;
import java.util.Collection;
import java.util.Map;

/**
 * Expression collects and returns a collection of EditorialNote InstanceSpecifications for
 * the OWL Class.
 *
 * @author Lonnie VanZandt
 * @version 1.0
 */
public class EditorialNoteExpression implements Expression, SmartListenerConfigurationProvider
{

    /**
     * Returns empty collection if the specified object is not an OWL Class. If
     * specified object is an OWL Class then returns the set of EditorialNotes
     * for that OWL Class.
     *
     * @param object the context Element from the current MD model.
     * @return collection of related EditorialNote InstanceSpecifications.
     */
    @Override
    public Object getValue (@CheckForNull RefObject object)
    {

        if (object instanceof com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Class)
        {

            final Class owlClass = (Class) object;

            FactPredicateByNameFinder factPredicateByNameFinder = new FactPredicateByNameFinder(owlClass);

            return factPredicateByNameFinder.findInstanceSpecifications("editorialNote");
        } else
        {
            return Lists.newArrayList();
        }
    }

    /**
     * {@inheritDoc} An implementation of the
     * com.nomagic.magicdraw.validation.SmartListenerConfigurationProvider
     * interface has to return the configuration that identifies the set of
     * properties that are important to the expression. The derived property
     * will be recalculated when one or more of these properties change.
     */
    @Override
    public Map<java.lang.Class<? extends Element>, Collection<SmartListenerConfig>> getListenerConfigurations ()
    {
        return FactPredicateListenerConfigFactory.getListenerConfigurations();
    }
}
