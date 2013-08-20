package com.nomagic.cameo.ontology.derivedproperties.expressions;

import com.google.common.base.Optional;
import com.google.common.collect.Lists;
import com.nomagic.uml2.ext.jmi.helpers.StereotypesHelper;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Element;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Property;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Slot;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.ValueSpecification;

import java.util.List;

/**
 * User: lvanzandt
 * Date: 7/24/13
 * Time: 7:48 PM
 */
public class StereotypedElement<UnadornedType extends Element>
{
    protected final UnadornedType unadornedElement;
    protected final String stereotypeName;

    public StereotypedElement (UnadornedType unadornedElement, String stereotypeName)
    {
        this.unadornedElement = unadornedElement;
        this.stereotypeName = stereotypeName;
    }

    public List<ValueSpecification> getTagValueValueSpecificationByName (String propertyName)
    {
        Optional<Property> stereoProperty = Optional.fromNullable(StereotypesHelper.findStereotypePropertyFor(unadornedElement, propertyName));

        if (stereoProperty.isPresent())
        {
            Optional<Slot> slot = Optional.fromNullable(StereotypesHelper.getSlot(unadornedElement, stereoProperty.get(), false));

            if (slot.isPresent())
            {
                return slot.get().getValue();
            }
        }

        return Lists.newArrayList();
    }
}
