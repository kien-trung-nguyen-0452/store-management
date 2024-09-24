package com.example.courseproject;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class HelloApplication extends Application {
    double x,y;
    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("public/views/layout.fxml"));
        Parent root = fxmlLoader.load();

        primaryStage.initStyle(StageStyle.UNDECORATED);

        root.setOnMousePressed(event -> {
          x=event.getSceneX();
          y=event.getSceneY();
        });

        root.setOnMouseDragged(event -> {
            primaryStage.setX(event.getScreenX() - x);
            primaryStage.setY(event.getScreenY() - y);
        });

        root.getStylesheets().add("com/example/courseproject/public/stylesheet/app.css");
        primaryStage.setTitle("Hello!");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}