package com.javaProject.shopManagement.exception;

import javafx.scene.control.Alert;

public class GlobalExceptionHandler {

    public static void handleException(Exception e) {
        System.err.println("error " + e.getMessage());
        showErrorDialog(e.getMessage());
    }

    private static void showErrorDialog(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Program caught an error");
        alert.setContentText(message);
        alert.showAndWait();
    }
}
