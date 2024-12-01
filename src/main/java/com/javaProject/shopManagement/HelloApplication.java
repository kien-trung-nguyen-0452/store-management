package com.javaProject.shopManagement;

import com.javaProject.shopManagement.util.motion.DraggableNode;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class HelloApplication extends Application {
    double x, y, offsetX, offsetY;
    double initialWidth, initialHeight;
    double initialX, initialY;

    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("public/views/layout.fxml"));
        Parent root = fxmlLoader.load();
        primaryStage.initStyle(StageStyle.DECORATED); // Remove window decoration
        primaryStage.setTitle("Shop Management");
        primaryStage.setScene(new Scene(root));
        DraggableNode draggableNode = new DraggableNode();
        draggableNode.makeStageDraggable(primaryStage, root);
        root.getStylesheets().add("com/javaProject/shopManagement/public/stylesheet/app.css");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}