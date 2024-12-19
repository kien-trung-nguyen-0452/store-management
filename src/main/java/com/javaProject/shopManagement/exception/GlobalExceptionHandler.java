package com.javaProject.shopManagement.exception;

import javafx.scene.control.Alert;

public class GlobalExceptionHandler {

    public enum ExceptionType {
        FILE_NOT_FOUND(FileNotFoundException.class, "File Not Found: "),
        IO(IOException.class, "I/O Error: "),
        SQL(SQLException.class, "SQL Error: "),
        UNEXPECTED(Exception.class, "An unexpected error occurred: ");

        private final Class<? extends Exception> exceptionClass;
        private final String messagePrefix;

        ExceptionType(Class<? extends Exception> exceptionClass, String messagePrefix) {
            this.exceptionClass = exceptionClass;
            this.messagePrefix = messagePrefix;
        }

        public static String getMessage(Exception e) {
            for (ExceptionType type : values()) {
                if (type.exceptionClass.isInstance(e)) {
                    return type.messagePrefix + e.getMessage();
                }
            }
            return UNEXPECTED.messagePrefix + e.getMessage();
        }
    }

    public static void handleException(Exception e) {

        showErrorDialog(ExceptionType.getMessage(e));
    }

    private static void showErrorDialog(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Program caught an error");
        alert.setContentText(message);
        alert.showAndWait();
    }
}