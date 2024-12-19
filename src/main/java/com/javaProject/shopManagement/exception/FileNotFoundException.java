package com.javaProject.shopManagement.exception;
public class FileNotFoundException extends Exception {
    public FileNotFoundException(String message) {
        super(message);
    }

    public FileNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}