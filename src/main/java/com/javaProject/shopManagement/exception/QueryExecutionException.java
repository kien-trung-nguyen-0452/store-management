package com.javaProject.shopManagement.exception;

public class QueryExecutionException extends RuntimeException {
    public QueryExecutionException(String message, Throwable cause) {
        super(message, cause);
    }

    public QueryExecutionException(String message) {
        super(message);
    }
}
