package com.javaProject.shopManagement.util.logger;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;

import java.util.Optional;

public class ErrorLogger {

    public static void showAlert(String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(alertType.name());
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static boolean getChoiceBox(String tile, String message) {
        Dialog<ButtonType> choiceBoxDialog = new Dialog<>();
        choiceBoxDialog.setTitle(tile);
        choiceBoxDialog.setHeaderText(null);
        choiceBoxDialog.setContentText(message);
        choiceBoxDialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        Optional<ButtonType> result = choiceBoxDialog.showAndWait();
        return result.isPresent() && result.get() == ButtonType.OK;
    }
}
