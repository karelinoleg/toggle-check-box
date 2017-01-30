package com.ugrp.javafx.control.breadcrumb;

import java.util.function.BiConsumer;
import java.util.function.Function;

import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.DoubleExpression;
import javafx.beans.property.ObjectProperty;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.shape.ClosePath;
import javafx.scene.shape.HLineTo;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;

/**
 * Button UI-control for breadcrumbs navigation bar.
 *
 * @param <T>
 *        a control value type.
 * @author Oleg Karelin
 */
public class ButtonBreadCrumb<T>
extends Button
{
    private final T value;

    /**
     * Creates button UI-control for breadcrumbs navigation bar.
     *
     * @param value
     *        the control value.
     * @param textFunction
     *        function for get display control text for current control value.
     * @param graphicFunction
     *        function for get control graphic for current control value.
     * @param onActionConsumer
     *        control value onAction consumer.
     * @param isFirst
     *        determines if it control is a first button on the bar.
     */
    public ButtonBreadCrumb(
        T value,
        ObjectProperty<Function<T, String>> textFunction,
        ObjectProperty<Function<T, Node>> graphicFunction,
        BiConsumer<T, Node> onActionConsumer,
        boolean isFirst)
    {
        this.value = value;

        textProperty().bind(Bindings.createStringBinding(
            () -> textFunction.get().apply(value), textFunction));
        graphicProperty().bind(Bindings.createObjectBinding(
            () -> graphicFunction.get().apply(value), graphicFunction));

        setOnAction(e -> onActionConsumer.accept(value, this));

        getStyleClass().add("bread-crumb");

        initButtonShape(isFirst);
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

    private void initButtonShape(
        boolean isFirst)
    {
        DoubleExpression height = heightProperty();
        DoubleExpression arrowWidth = height.divide(3);

        // Draws SVG-path to give button arrow shape.
        Path path = new Path();

        MoveTo start = new MoveTo(0, 0);

        HLineTo lineToRight = new HLineTo();
        lineToRight.xProperty().bind(widthProperty().subtract(arrowWidth));

        LineTo lineToRightDown = new LineTo();
        lineToRightDown.xProperty()
            .bind(lineToRight.xProperty().add(arrowWidth));
        lineToRightDown.yProperty().bind(height.divide(2));

        LineTo lineToLeftDown = new LineTo();
        lineToLeftDown.xProperty().bind(lineToRight.xProperty());
        lineToLeftDown.yProperty().bind(height);

        HLineTo lineToLeft = new HLineTo(0);

        LineTo lineToRightUp = new LineTo();
        lineToRightUp.xProperty().bind(arrowWidth);
        lineToRightUp.yProperty().bind(height.divide(2));

        ClosePath closePath = new ClosePath();

        path.getElements().addAll(start, lineToRight, lineToRightDown,
            lineToLeftDown, lineToLeft);

        if (!isFirst)
        {
            path.getElements().add(lineToRightUp);

            arrowWidth.addListener(i ->
                Platform.runLater(() ->
                    HBox.setMargin(this, new Insets(
                        0d, 0d, 0d, -arrowWidth.doubleValue() + 2))));
        }

        path.getElements().add(closePath);

        setShape(path);
    }
}
