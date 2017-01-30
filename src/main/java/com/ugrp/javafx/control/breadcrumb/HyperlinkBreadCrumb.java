package com.ugrp.javafx.control.breadcrumb;

import java.util.function.BiConsumer;
import java.util.function.Function;

import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.scene.Node;
import javafx.scene.control.Hyperlink;

/**
 * Hyperlink UI-control for breadcrumbs navigation bar.
 *
 * @param <T>
 *        a control value type.
 * @author Oleg Karelin
 */
public class HyperlinkBreadCrumb<T>
extends Hyperlink
{
    private final T value;

    /**
     * Creates hyperlink UI-control for breadcrumbs navigation bar.
     *
     * @param value
     *        the control value.
     * @param textFunction
     *        function for get display control text for current control value.
     * @param graphicFunction
     *        function for get control graphic for current control value.
     * @param onActionConsumer
     *        control value onAction consumer.
     */
    public HyperlinkBreadCrumb(
        T value,
        ObjectProperty<Function<T, String>> textFunction,
        ObjectProperty<Function<T, Node>> graphicFunction,
        BiConsumer<T, Node> onActionConsumer)
    {
        this.value = value;

        getStyleClass().add("bread-crumb");

        textProperty().bind(
            Bindings.createStringBinding(
                () -> textFunction.get().apply(value),
                textFunction));
        graphicProperty().bind(
            Bindings.createObjectBinding(
                () -> graphicFunction.get().apply(value),
                graphicFunction));

        setOnAction(e -> onActionConsumer.accept(value, this));

        getStyleClass().add("bread-crumb");
    }

    /**
     * Gets the control value.
     *
     * @return the control value.
     */
    public T getValue()
    {
        return value;
    }
}
