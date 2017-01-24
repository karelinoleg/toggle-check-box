package com.ugrp.javafx.control;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Skin;

/**
 * UI-control &laquo;Toggle check box&raquo;.
 *
 * @author Oleg Karelin
 */
public class ToggleCheckBox
extends CheckBox
{
    private final StringProperty checkedText;

    private final StringProperty uncheckedText;

    /**
     * Creates UI-control &laquo;Toggle check box&raquo;.
     */
    public ToggleCheckBox()
    {
        getStylesheets().add(ToggleCheckBox.class
            .getResource("togglecheckbox.css").toExternalForm());

        checkedText = new SimpleStringProperty();
        uncheckedText = new SimpleStringProperty();
    }

    /**
     * Creates UI-control &laquo;Toggle check box&raquo; with specified text
     * captions.
     *
     * @param checkedCaption
     *        caption for control when control state is selected.
     * @param uncheckedCaption
     *        caption for control when control state is unselected.
     */
    public ToggleCheckBox(
        String checkedCaption,
        String uncheckedCaption)
    {
        this();

        setCheckedText(checkedCaption);
        setUncheckedText(uncheckedCaption);
    }

    @Override
    protected Skin<?> createDefaultSkin()
    {
        return new ToggleCheckBoxSkin(this);
    }

    /**
     * Gets the string value to be displayed by control when when control state
     * is selected.
     *
     * @return the string value to be displayed by control when when control
     *         state is selected.
     */
    public String getCheckedText()
    {
        return checkedText.getValue();
    }

    /**
     * Gets a string property to be displayed by control when when control state
     * is selected.
     *
     * @return a string property to be displayed by control when when control
     *         state is selected.
     */
    public StringProperty checkedTextProperty()
    {
        return checkedText;
    }

    /**
     * Sets the string value to be displayed by control when when control state
     * is selected.
     *
     * @param checkedText
     *        the string value to be displayed by control when when control
     *        state is selected.
     */
    public void setCheckedText(
        String checkedText)
    {
        this.checkedText.setValue(checkedText);
    }

    /**
     * Gets the string value to be displayed by control when when control state
     * is unselected.
     *
     * @return the string value to be displayed by control when when control
     *         state is unselected.
     */
    public String getUncheckedText()
    {
        return uncheckedText.getValue();
    }

    /**
     * Gets a string property to be displayed by control when when control state
     * is unselected.
     *
     * @return a string property to be displayed by control when when control
     *         state is unselected.
     */
    public StringProperty uncheckedTextProperty()
    {
        return uncheckedText;
    }

    /**
     * Sets the string value to be displayed by control when when control state
     * is unselected.
     *
     * @param uncheckedText
     *        the string value to be displayed by control when when control
     *        state is unselected.
     */
    public void setUncheckedText(
        String uncheckedText)
    {
        this.uncheckedText.setValue(uncheckedText);
    }
}
