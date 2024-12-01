package com.javaProject.shopManagement.util.motion;

import javafx.scene.Node;
import javafx.stage.Stage;

public class DraggableNode {
    double x;
    double y;

    public DraggableNode(){}

    public void makeNodeDraggable(Node node) {
        node.setOnMousePressed(event -> {
            x = event.getSceneX() - node.getLayoutX();
            y = event.getSceneY() - node.getLayoutY();
        });
        node.setOnMouseDragged(event -> {
            node.setLayoutX(event.getScreenX() - x);
            node.setLayoutY(event.getScreenY() - y);
        });
    }

    public void makeStageDraggable(Stage stage, Node scene) {
        scene.setOnMousePressed(event -> {
            x = event.getSceneX();
            y = event.getSceneY();
        });

        scene.setOnMouseDragged(event -> {
            stage.setX(event.getScreenX() - x);
            stage.setY(event.getScreenY() - y);
        });
    }

}


