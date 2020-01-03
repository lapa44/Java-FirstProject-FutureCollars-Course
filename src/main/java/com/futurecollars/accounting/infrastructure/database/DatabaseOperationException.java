package com.futurecollars.accounting.infrastructure.database;

public class DatabaseOperationException extends Exception {
  DatabaseOperationException(String message, Throwable cause) {
    super(message, cause);
  }
}
