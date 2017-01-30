package com.ugrp.javafx.control.breadcrumb;

import java.util.function.BiConsumer;
import java.util.function.Function;

import javafx.beans.property.ObjectProperty;
import javafx.scene.Node;

/**
 * Breadcrumbs navigation bar. Hyperlinks ({@link HyperlinkBreadCrumb}) are used
 * as navigation buttons.
 *
 * @param <T>
 *        a control value type.
 * @author Oleg Karelin
 */
public class HyperlinkBreadCrumbsBar<T>
extends AbstractBreadCrumbsBar<T>
{
    /**
     * Creates a breadcrumbs navigation bar.
     */
    public HyperlinkBreadCrumbsBar()
    {
    }

    @Override
    protected Node createBreadCrumb(
        T item,
        ObjectProperty<Function<T, String>> textFunction,
        ObjectProperty<Function<T, Node>> graphicFunction,
        BiConsumer<T, Node> onActionConsumer,
        boolean isFirst)
    {
        return new HyperlinkBreadCrumb<>(item, textFunction, graphicFunction,
            onActionConsumer);
    }
}
