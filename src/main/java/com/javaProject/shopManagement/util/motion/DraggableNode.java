package com.javaProject.shopManagement.util.motion;

import javafx.scene.Node;

public class DraggableNode {
    static double  x;
    static double  y;

    public static void setDraggable(Node node){
        node.setOnMousePressed(event -> {
            x = event.getSceneX() -node.getLayoutX();
            y = event.getSceneY() -node.getLayoutY() ;
        });
        node.setOnMouseDragged(event -> {
            node.setLayoutX(event.getScreenX() - x);
            node.setLayoutY(event.getScreenY() - y);
        });
    }

}
