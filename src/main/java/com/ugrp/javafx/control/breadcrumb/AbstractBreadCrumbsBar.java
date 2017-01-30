package com.ugrp.javafx.control.breadcrumb;

import java.util.Collection;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.Node;
import javafx.scene.layout.HBox;

/**
 * Common part of BreadCrumbsBar implementation.
 *
 * @param <T>
 *        a control value type.
 * @author Oleg Karelin
 */
public abstract class AbstractBreadCrumbsBar<T>
extends HBox
{
    private final ObjectProperty<Function<T, String>> textFunction;

    private final ObjectProperty<Function<T, Node>> graphicFunction;

    private final ObjectProperty<Consumer<T>> onActionConsumer;

    private final ObjectProperty<Supplier<Node>> separatorSupplier;

    /**
     * Creates a breadcrumbs navigation bar.
     */
    public AbstractBreadCrumbsBar()
    {
        textFunction = new SimpleObjectProperty<>(Object::toString);
        graphicFunction = new SimpleObjectProperty<>(t -> null);
        onActionConsumer = new SimpleObjectProperty<>(t -> { });
        separatorSupplier = new SimpleObjectProperty<>(null);

        getStylesheets().add(AbstractBreadCrumbsBar.class
            .getResource("bread-crumbs-bar.css").toExternalForm());

        getStyleClass().add("bread-crumbs-bar");
    }

    /**
     * Creates button UI-control for breadcrumbs navigation bar.
     *
     * @param item
     *        the control value.
     * @param textFunction
     *        function for get display control text for current control value.
     * @param graphicFunction
     *        function for get control graphic for current control value.
     * @param onActionConsumer
     *        control value onAction consumer.
     * @param isFirst
     *        determines if it control is a first button on the bar.
     * @return UI-control item for breadcrumbs navigation bar.
     */
    protected abstract Node createBreadCrumb(
        T item,
        ObjectProperty<Function<T, String>> textFunction,
        ObjectProperty<Function<T, Node>> graphicFunction,
        BiConsumer<T, Node> onActionConsumer,
        boolean isFirst);

    /**
     * Adds to the bar a new UI-control for specified item.
     *
     * @param item
     *        item to add.
     */
    public void addBreadCrumb(
        T item)
    {
        boolean isFirst = getChildren().isEmpty();

        Supplier<Node> nodeSupplier;
        if (!isFirst && (nodeSupplier = getSeparatorSupplier()) != null)
        {
            Node separator = nodeSupplier.get();
            separator.getStyleClass().add("separator");
            getChildren().add(separator);
        }

        getChildren().add(
            createBreadCrumb(
                item,
                textFunction,
                graphicFunction,
                (t, n) -> {
                    bobtail(n);
                    onActionConsumer.get().accept(t);
                },
                isFirst));
    }

    private void bobtail(
        Node node)
    {
        int index = getChildren().indexOf(node);

        List<Node> head = getChildren().stream().limit(index + 1)
            .collect(Collectors.toList());

        getChildren().setAll(head);
    }

    /**
     * Adds to the bar a new UI-controls for specified items.
     *
     * @param items
     *        items collection to add.
     */
    public void addBreadCrumbs(
        Collection<T> items)
    {
        items.forEach(this::addBreadCrumb);
    }

    /**
     * Sets to the bar a new UI-controls for specified items.
     *
     * @param items
     *        items collection to set.
     */
    public void setBreadCrumbs(
        Collection<T> items)
    {
        getChildren().clear();
        addBreadCrumbs(items);
    }

    /**
     * Gets the function for get display control text for current control value.
     *
     * @return the function for get display control text for current control
     *         value.
     */
    public Function<T, String> getTextFunction()
    {
        return textFunction.get();
    }

    /**
     * Gets the function for get display control text for current control value.
     *
     * @return the function for get display control text for current control
     *         value.
     */
    public ObjectProperty<Function<T, String>> textFunctionProperty()
    {
        return textFunction;
    }

    /**
     * Sets the function for get display control text for current control value.
     *
     * @param textFunction
     *        the function for get display control text for current control
     *        value.
     */
    public void setTextFunction(
        Function<T, String> textFunction)
    {
        this.textFunction.set(textFunction);
    }

    /**
     * Gets the function for get control graphic for current control value.
     *
     * @return the function for get control graphic for current control value.
     */
    public Function<T, Node> getGraphicFunction()
    {
        return graphicFunction.get();
    }

    /**
     * Gets the function for get control graphic for current control value.
     *
     * @return the function for get control graphic for current control value.
     */
    public ObjectProperty<Function<T, Node>> graphicFunctionProperty()
    {
        return graphicFunction;
    }

    /**
     * Sets the function for get control graphic for current control value.
     *
     * @param graphicFunction
     *        the function for get control graphic for current control value.
     */
    public void setGraphicFunction(
        Function<T, Node> graphicFunction)
    {
        this.graphicFunction.set(graphicFunction);
    }

    /**
     * Gets the control value onAction consumer.
     *
     * @return the control value onAction consumer.
     */
    public Consumer<T> getOnActionConsumer()
    {
        return onActionConsumer.get();
    }

    /**
     * Gets the control value onAction consumer.
     *
     * @return the control value onAction consumer.
     */
    public ObjectProperty<Consumer<T>> onActionConsumerProperty()
    {
        return onActionConsumer;
    }

    /**
     * Sets the control value onAction consumer.
     *
     * @param onActionConsumer
     *        the control value onAction consumer.
     */
    public void setOnActionConsumer(
        Consumer<T> onActionConsumer)
    {
        this.onActionConsumer.set(onActionConsumer);
    }

    /**
     * Gets the supplier for separator items creating.
     *
     * @return the supplier for separator items creating.
     */
    public Supplier<Node> getSeparatorSupplier()
    {
        return separatorSupplier.get();
    }

    /**
     * Gets the supplier for separator items creating.
     *
     * @return the supplier for separator items creating.
     */
    public ObjectProperty<Supplier<Node>> separatorSupplierProperty()
    {
        return separatorSupplier;
    }

    /**
     * Sets the supplier for separator items creating.
     *
     * @param separatorSupplier
     *        the supplier for separator items creating.
     */
    public void setSeparatorSupplier(
        Supplier<Node> separatorSupplier)
    {
        this.separatorSupplier.set(separatorSupplier);
    }
}
