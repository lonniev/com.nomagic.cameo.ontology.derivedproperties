package com.nomagic.cameo.partners.omg.fibo.plugins.derivedproperties.expressions.owlrestriction;

import com.google.common.base.Optional;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.nomagic.cameo.partners.omg.fibo.plugins.derivedproperties.expressions.element
        .ConceptTermByAppliedStereoFinder;
import com.nomagic.magicdraw.validation.SmartListenerConfigurationProvider;
import com.nomagic.uml2.ext.jmi.reflect.Expression;
import com.nomagic.uml2.ext.jmi.smartlistener.SmartListenerConfig;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Class;
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
    /**
     * Returns empty collection if the specified object is not an Class.
     * If the specified object is an OWL Restriction Class then it returns a collection
     * of the LiteralStrings in the model that provide an alias for the type of the element.
     *
     * @param object the context Element from the current MD model.
     * @return collection of LiteralStrings.
     */
    @Override
    public Object getValue (@CheckForNull RefObject object)
    {
        List<LiteralString> values = Lists.newArrayList();

        if (object instanceof Class)
        {
            final Class owlClass = (Class) object;

            Optional<LiteralString> literalString = ConceptTermByAppliedStereoFinder.findConceptType(Optional.of
                    (owlClass));

            values.add(literalString.get());
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
    public Map<java.lang.Class<? extends Element>, Collection<SmartListenerConfig>> getListenerConfigurations ()
    {
        Map<java.lang.Class<? extends Element>, Collection<SmartListenerConfig>> configs = Maps.newHashMap();

        Collection<SmartListenerConfig> listeners = Lists.newArrayList();
        SmartListenerConfig smartListenerCfg = new SmartListenerConfig();

        // if the name of the element changes
        smartListenerCfg.listenTo(PropertyNames.APPLIED_STEREOTYPE_INSTANCE);

        listeners.add(smartListenerCfg);

        configs.put(Class.class, listeners);

        return configs;
    }
}
