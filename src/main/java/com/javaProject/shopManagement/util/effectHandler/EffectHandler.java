package com.javaProject.shopManagement.util.effectHandler;

import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.Node;
import javafx.util.Duration;

public class EffectHandler {
    public static void getEffect(EffectType effectType, Node node) {
        switch (effectType) {

            case FADE_IN -> applyFadeInEffect(node);
            case FADE_OUT -> applyFadeOutEffect(node);
            case SLIDE_IN -> applySlideInEffect(node);
            case SLIDE_OUT -> applySlideOutEffect(node);
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



}
