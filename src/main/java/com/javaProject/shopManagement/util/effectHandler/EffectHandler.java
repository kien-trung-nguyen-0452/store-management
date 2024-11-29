package com.javaProject.shopManagement.util.effectHandler;

import javafx.animation.*;
import javafx.scene.Node;
import javafx.util.Duration;

public class EffectHandler {
    public static void getEffect(EffectType effectType, Node node) {
        switch (effectType) {

            case FADE_IN -> applyFadeInEffect(node);
            case FADE_OUT -> applyFadeOutEffect(node);
            case SLIDE_IN -> applySlideInEffect(node);
            case SLIDE_OUT -> applySlideOutEffect(node);
            case SCROLL_UP -> applyScrollUpEffect(node);
            case SCROLL_DOWN -> applyScrollDownEffect(node);
            case SCALE_UP -> applyScaleUpEffect(node);
            case SCALE_DOWN -> applyScaleDownEffect(node);
        }
    }

    private static void applyFadeInEffect(Node node) {
        FadeTransition fadeTransition = new FadeTransition(Duration.millis(1000), node);
        fadeTransition.setFromValue(-50);
        fadeTransition.setToValue(1);
        fadeTransition.play();

    }
    private static void applyFadeOutEffect(Node node) {
        FadeTransition fadeTransition = new FadeTransition(Duration.millis(1000), node);
        fadeTransition.setFromValue(1);
        fadeTransition.setToValue(0);
        fadeTransition.play();

    }
    private static void applySlideInEffect(Node node) {
        TranslateTransition slideIn = new TranslateTransition(Duration.millis(2000), node);
        slideIn.setFromX(0);
        slideIn.setToX(node.getLayoutBounds().getWidth());


        FadeTransition fadeOut = new FadeTransition(Duration.seconds(1), node);
        fadeOut.setFromValue(0.0);
        fadeOut.setToValue(1.0);
        slideIn.play();
        fadeOut.play();
    }
    private static void applySlideOutEffect(Node node) {
        TranslateTransition slideOut = new TranslateTransition(Duration.millis(1000), node);
        slideOut.setFromX(1);
        slideOut.setToX(node.getLayoutBounds().getWidth());


        FadeTransition fadeOut = new FadeTransition(Duration.seconds(1), node);
        fadeOut.setFromValue(1.0);
        fadeOut.setToValue(0.0);

        slideOut.play();
        fadeOut.play();
    }

    private static void applyScrollUpEffect(Node node) {
        TranslateTransition scrollUp = new TranslateTransition(Duration.millis(2000), node);
        scrollUp.setFromY(-10);
        scrollUp.setToY(0);
        FadeTransition fadeOut = new FadeTransition(Duration.seconds(1), node);
        scrollUp.play();
    }

    private static void applyScrollDownEffect(Node node) {
        TranslateTransition scrollDown = new TranslateTransition(Duration.millis(300), node);
        scrollDown.setFromY(-50);
        scrollDown.setToY(0);
        FadeTransition fadeIn = new FadeTransition(Duration.seconds(1), node);
        fadeIn.setFromValue(0);
        fadeIn.setToValue(1.0);
        fadeIn.play();
        scrollDown.play();
    }

    private static void applyScaleUpEffect(Node node) {
        ScaleTransition scaleUp = new ScaleTransition(Duration.millis(400), node);
        scaleUp.setFromY(0);
        scaleUp.setToY(1);
        scaleUp.setFromX(0);
        scaleUp.setToX(1);
        scaleUp.play();

    }
    private static void applyScaleDownEffect(Node node) {
        ScaleTransition scaleDown = new ScaleTransition(Duration.millis(400), node);
        scaleDown.setFromY(1);
        scaleDown.setFromX(1);
        scaleDown.setToY(0);
        scaleDown.setToX(0);
        scaleDown.play();

    }

    public static void setShowUP(Node node, boolean isVisible){
        if(isVisible){
            getEffect(EffectType.SCALE_DOWN, node);
            Timeline timeline = new Timeline(new KeyFrame(Duration.millis(400), event -> {
                node.setVisible(false);
            })
            );
            timeline.play();
        }
        else {
            node.setVisible(true);
            getEffect(EffectType.SCALE_UP, node);
        }
    }



}
