package com.javaProject.shopManagement.exception;

public class SQLException extends Exception {
  public SQLException(String message) {
    super(message);
  }

  public SQLException(String message, Throwable cause) {
    super(message, cause);
  }
}
