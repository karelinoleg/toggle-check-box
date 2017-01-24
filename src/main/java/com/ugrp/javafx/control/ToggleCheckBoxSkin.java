package com.ugrp.javafx.control;

import com.sun.javafx.scene.control.behavior.ButtonBehavior;
import com.sun.javafx.scene.control.skin.LabeledSkinBase;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.binding.ObjectBinding;
import javafx.geometry.NodeOrientation;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.util.Duration;

/**
 * Skin for {@link ToggleCheckBox} control.
 *
 * @author Oleg Karelin
 */
class ToggleCheckBoxSkin
extends LabeledSkinBase<ToggleCheckBox, ButtonBehavior<ToggleCheckBox>>
{
    private static final double BOX_HEIGHT = 32;

    private static final double THUMB_WIDTH = 24;

    private static final double THUMB_HEIGHT = 24;

    private static final double THUMB_PADDING = 4;

    private static final double SLIDE_DURATION = 180;

    private final StackPane box;

    private final DoubleBinding halfWidth;

    private Text checkedText;

    private Text uncheckedText;

    private Region thumb;

    private ObjectBinding<Timeline> checkTimeline;

    private ObjectBinding<Timeline> uncheckTimeline;

    /**
     * Creates skin for {@link ToggleCheckBox} control.
     *
     * @param slideBox
     *        UI-control {@link ToggleCheckBox}.
     */
    ToggleCheckBoxSkin(
        ToggleCheckBox slideBox)
    {
        super(slideBox, new ButtonBehavior<>(slideBox));

        box = new StackPane();

        halfWidth = box.widthProperty().subtract(THUMB_WIDTH)
            .divide(2).subtract(THUMB_PADDING);

        initGraphics(slideBox);
        initAnimations();
        registerListeners();
    }

    private void initGraphics(
        ToggleCheckBox slideBox)
    {
        checkedText = new Text();
        checkedText.textProperty().bind(slideBox.checkedTextProperty());
        checkedText.getStyleClass().setAll("checked-text");
        checkedText.setNodeOrientation(NodeOrientation.LEFT_TO_RIGHT);
        checkedText.setTranslateX(-THUMB_WIDTH / 2);

        uncheckedText = new Text();
        uncheckedText.textProperty().bind(slideBox.uncheckedTextProperty());
        uncheckedText.getStyleClass().setAll("unchecked-text");
        uncheckedText.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
        uncheckedText.setTranslateX(THUMB_WIDTH / 2);

        thumb = new Region();
        thumb.getStyleClass().setAll("thumb");
        thumb.setMinSize(THUMB_WIDTH, THUMB_HEIGHT);
        thumb.setPrefSize(THUMB_WIDTH, THUMB_HEIGHT);
        thumb.setMaxSize(THUMB_WIDTH, THUMB_HEIGHT);

        box.getStyleClass().setAll("box");
        box.getChildren().addAll(checkedText, uncheckedText, thumb);

        updateChildren();
    }

    private void initAnimations()
    {
        checkTimeline = Bindings.createObjectBinding(
            () -> createCheckTimeline(halfWidth.getValue()),
            halfWidth);

        uncheckTimeline = Bindings.createObjectBinding(
            () -> createUncheckTimeline(halfWidth.getValue()),
            halfWidth);
    }

    private void registerListeners()
    {
        getSkinnable().selectedProperty().addListener(observable -> toggle());
    }

    @Override
    protected void updateChildren()
    {
        super.updateChildren();

        if (box != null)
        {
            getChildren().add(box);
        }
    }

    @Override
    protected double computeMinWidth(
        double height,
        double topInset,
        double rightInset,
        double bottomInset,
        double leftInset)
    {
        return super.computeMinWidth(height, topInset, rightInset, bottomInset,
            leftInset) + snapSize(box.minWidth(-1));
    }

    @Override
    protected double computeMinHeight(
        double width,
        double topInset,
        double rightInset,
        double bottomInset,
        double leftInset)
    {
        return Math.max(
            super.computeMinHeight(width - box.minWidth(-1), topInset,
                rightInset, bottomInset, leftInset),
            topInset + box.minHeight(-1) + bottomInset);
    }

    @Override
    protected double computePrefWidth(
        double height,
        double topInset,
        double rightInset,
        double bottomInset,
        double leftInset)
    {
        return super.computePrefWidth(height, topInset, rightInset, bottomInset,
            leftInset) + snapSize(box.prefWidth(-1) + THUMB_WIDTH - 2d);
    }

    @Override
    protected double computePrefHeight(
        double width,
        double topInset,
        double rightInset,
        double bottomInset,
        double leftInset)
    {
        return Math.max(
            super.computePrefHeight(width - box.prefWidth(-1), topInset,
                rightInset, bottomInset, leftInset),
            topInset + box.prefHeight(-1) + bottomInset);
    }

    @Override
    protected void layoutChildren(
        final double x,
        final double y,
        final double w,
        final double h)
    {
        final ToggleCheckBox checkBox = getSkinnable();

        final double computedWidth = Math.max(
            checkBox.prefWidth(-1), checkBox.minWidth(-1));
        thumb.resize(THUMB_WIDTH, THUMB_HEIGHT);
        box.resize(computedWidth, BOX_HEIGHT);

        if (checkBox.isSelected())
        {
            uncheckedText.setOpacity(0);
            thumb.setTranslateX(halfWidth.doubleValue());
        }
        else
        {
            checkedText.setOpacity(0);
            thumb.setTranslateX(-halfWidth.doubleValue());
        }
    }

    private void toggle()
    {
        if (getSkinnable().isSelected())
        {
            checkTimeline.getValue().play();
        }
        else
        {
            uncheckTimeline.getValue().play();
        }
    }

    private Timeline createCheckTimeline(
        Double halfWidth)
    {
        final KeyValue thumbStartTranslateCheckKeyValue = new KeyValue(
            thumb.translateXProperty(), -halfWidth, Interpolator.EASE_BOTH);
        final KeyValue thumbEndTranslateCheckKeyValue = new KeyValue(
            thumb.translateXProperty(), halfWidth, Interpolator.EASE_BOTH);

        final KeyValue checkedTextStartOpacityCheckKeyValue = new KeyValue(
            checkedText.opacityProperty(), 0, Interpolator.EASE_BOTH);
        final KeyValue checkTextEndOpacityCheckKeyValue = new KeyValue(
            checkedText.opacityProperty(), 1, Interpolator.EASE_BOTH);

        final KeyValue uncheckedTextStartOpacityCheckKeyValue = new KeyValue(
            uncheckedText.opacityProperty(), 1, Interpolator.EASE_BOTH);
        final KeyValue uncheckedTextEndOpacityCheckKeyValue = new KeyValue(
            uncheckedText.opacityProperty(), 0, Interpolator.EASE_BOTH);

        final KeyFrame kfStart = new KeyFrame(
            Duration.ZERO,
            thumbStartTranslateCheckKeyValue,
            checkedTextStartOpacityCheckKeyValue,
            uncheckedTextStartOpacityCheckKeyValue);
        final KeyFrame kfEnd = new KeyFrame(
            Duration.millis(SLIDE_DURATION),
            thumbEndTranslateCheckKeyValue,
            checkTextEndOpacityCheckKeyValue,
            uncheckedTextEndOpacityCheckKeyValue);

        final Timeline timeline = new Timeline();
        timeline.getKeyFrames().setAll(kfStart, kfEnd);

        return timeline;
    }

    private Timeline createUncheckTimeline(
        Double halfWidth)
    {
        final KeyValue thumbStartTranslateUncheckKeyValue = new KeyValue(
            thumb.translateXProperty(), halfWidth);
        final KeyValue thumbEndTranslateUncheckKeyValue = new KeyValue(
            thumb.translateXProperty(), -halfWidth);

        final KeyValue checkedTextStartOpacityUncheckKeyValue = new KeyValue(
            checkedText.opacityProperty(), 1);
        final KeyValue checkedTextEndOpacityUncheckKeyValue = new KeyValue(
            checkedText.opacityProperty(), 0);

        final KeyValue uncheckedTextStartOpacityUncheckKeyValue = new KeyValue(
            uncheckedText.opacityProperty(), 0);
        final KeyValue uncheckedTextEndOpacityUncheckKeyValue = new KeyValue(
            uncheckedText.opacityProperty(), 1);

        final KeyFrame kfStart = new KeyFrame(
            Duration.ZERO,
            thumbStartTranslateUncheckKeyValue,
            checkedTextStartOpacityUncheckKeyValue,
            uncheckedTextStartOpacityUncheckKeyValue);
        final KeyFrame kfEnd = new KeyFrame(
            Duration.millis(SLIDE_DURATION),
            thumbEndTranslateUncheckKeyValue,
            checkedTextEndOpacityUncheckKeyValue,
            uncheckedTextEndOpacityUncheckKeyValue);

        final Timeline timeline = new Timeline();
        timeline.getKeyFrames().setAll(kfStart, kfEnd);

        return timeline;
    }
}
