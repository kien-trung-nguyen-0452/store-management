package com.javaProject.shopManagement;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
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
        primaryStage.initStyle(StageStyle.UNDECORATED); // Remove window decoration



        initialWidth = primaryStage.getWidth();
        initialHeight = primaryStage.getHeight();
        initialX = primaryStage.getX();
        initialY = primaryStage.getY();

        root.setOnMousePressed(event -> {
            x = event.getSceneX();
            y = event.getSceneY();
            offsetX = primaryStage.getWidth() - event.getSceneX(); // Để tính toán khi resize từ phải
            offsetY = primaryStage.getHeight() - event.getSceneY(); // Để tính toán khi resize từ dưới
        });



        root.setOnMouseMoved(event -> {
            double width = root.getLayoutBounds().getWidth();
            double height = root.getLayoutBounds().getHeight();

            // Set cursor to resize based on the mouse position near edges
            if (event.getX() > width - 10 && event.getY() > height - 10) {
                root.setCursor(Cursor.SE_RESIZE); // Bottom-right corner
            } else if (event.getX() > width - 10) {
                root.setCursor(Cursor.H_RESIZE); // Right edge
            } else if (event.getY() > height - 10) {
                root.setCursor(Cursor.V_RESIZE); // Bottom edge
            } else if (event.getX() < 10) {
                root.setCursor(Cursor.W_RESIZE); // Left edge
            } else if (event.getY() < 10) {
                root.setCursor(Cursor.N_RESIZE); // Top edge
            } else {
                root.setCursor(Cursor.DEFAULT); // Default cursor when not near edges
            }
        });


        root.setOnMouseDragged(event -> {
                if (root.getCursor() == Cursor.H_RESIZE) {
                    double width = root.getLayoutBounds().getWidth();
                    double height = root.getLayoutBounds().getHeight();

                } else if (root.getCursor() == Cursor.V_RESIZE) {
                    double newHeight = initialHeight + -offsetY;
                    primaryStage.setHeight(newHeight);
                } else if (root.getCursor() == Cursor.SE_RESIZE) {
                    double newWidth = initialWidth - offsetX;
                    double newHeight = initialHeight - offsetY;
                    primaryStage.setWidth(newWidth);
                    primaryStage.setHeight(newHeight);
                } else if (root.getCursor() == Cursor.W_RESIZE) {
                    double newWidth = initialWidth - offsetX;
                    primaryStage.setWidth(newWidth);
                    primaryStage.setX(initialX); // Adjust X position
                } else if (root.getCursor() == Cursor.N_RESIZE) {
                    double newHeight = initialHeight -offsetY;
                    primaryStage.setHeight(newHeight);
                    primaryStage.setY(initialY + (event.getSceneY())); // Adjust Y position
                } else {
                    // Move the window when not resizing
                    primaryStage.setX(event.getScreenX() - x);
                    primaryStage.setY(event.getScreenY() - y);
                }

        });
        primaryStage.setScene(new Scene(root));
        root.getStylesheets().add("com/javaProject/shopManagement/public/stylesheet/app.css");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}