package com.javaProject.shopManagement.util.validator;

import io.github.palexdev.materialfx.controls.MFXDatePicker;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.scene.control.Label;

import java.time.LocalDate;

public class InputValidator {
    public static boolean isInteger(MFXTextField textField, Label errorLabel) {
        String input = textField.getText();
        boolean isValid = true;
        String message = "";
        try {
            Integer.parseUnsignedInt(input);
        } catch (NumberFormatException e) {
            isValid = false;
            message = "Require number!";

        }
        updateStyleAndError(errorLabel, isValid, message, textField);
        return isValid;
    }

    public static boolean isNotNull(MFXTextField textField, Label errorLabel) {
        String input = textField.getText();
        boolean isValid =true;
        String message = "";
        if (input==null || input.isEmpty()) {
            isValid = false;
            message = "Input is empty!";
        }
        updateStyleAndError(errorLabel, isValid, message, textField);

        return isValid;
    }

    public static boolean isDouble(MFXTextField textField, Label errorLabel) {
        String input = textField.getText();
        boolean isValid = true;
        String message = "";
        try {
            Double.parseDouble(input);
        } catch (NumberFormatException e) {
            isValid = false;
            message = "Invalid input!";
        }
        updateStyleAndError(errorLabel, isValid, message, textField);
        return isValid;
    }

    public static boolean isNotNull(MFXDatePicker datePicker, Label errorLabel) {
        LocalDate localDate = datePicker.getValue();
        boolean isValid =true;
        String message = "";
        if (localDate==null) {
            isValid = false;
            message = "Please pick a date";
        }
        updateStyleAndError(errorLabel, isValid, message, datePicker);

        return isValid;
    }

    public static boolean isNotBefore(MFXDatePicker datePicker, LocalDate validationDate, Label errorLabel) {
        LocalDate selectedDate = datePicker.getValue();
        boolean isValid = true;
        String message = "";
        if (selectedDate.isBefore(validationDate)) {
            isValid = false;
            message = "Can not pick a date before current";
        }
        updateStyleAndError(errorLabel, isValid, message, datePicker);
        return isValid;
    }

    private static void updateStyleAndError(Label errorLabel, boolean isValid, String errorMessage, MFXTextField textField) {
        if (isValid) {
            setValidStyle(textField);
            errorLabel.setVisible(false);
        } else {
            setInvalidStyle(textField);
            errorLabel.setText(errorMessage);
            errorLabel.setVisible(true);
            errorLabel.getStyleClass().add("error-label");
        }
    }

    private static void updateStyleAndError(Label errorLabel, boolean isValid, String errorMessage, MFXDatePicker datePicker) {
        if (isValid) {
            setValidStyle(datePicker);
            errorLabel.setVisible(false);
        } else {
            setInvalidStyle(datePicker);
            errorLabel.setText(errorMessage);
            errorLabel.setVisible(true);
            errorLabel.getStyleClass().add("error-label");
        }
    }

    // Phương thức áp dụng style cho đầu vào hợp lệ
    private static void setValidStyle(MFXTextField textField) {
        textField.getStyleClass().removeAll("invalid-input");
        if (!textField.getStyleClass().contains("valid-input")) {
            textField.getStyleClass().add("valid-input");
        }
    }

    private static void setValidStyle(MFXDatePicker datePicker) {
        datePicker.getStyleClass().removeAll("invalid-input");
        if (!datePicker.getStyleClass().contains("valid-input")) {
            datePicker.getStyleClass().add("valid-input");
        }
    }

    // Phương thức áp dụng style cho đầu vào không hợp lệ
    private static void setInvalidStyle(MFXTextField textField) {
        textField.getStyleClass().removeAll("valid-input");
        if (!textField.getStyleClass().contains("invalid-input")) {
            textField.getStyleClass().add("invalid-input");
        }
    }

    private static void setInvalidStyle(MFXDatePicker datePicker) {
        datePicker.getStyleClass().removeAll("valid-input");
        if (!datePicker.getStyleClass().contains("invalid-input")) {
            datePicker.getStyleClass().add("invalid-input");
        }
    }


}

