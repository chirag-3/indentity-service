package com.github.chirag.identityservice.exception;

public class InsufficientDataException extends RuntimeException {
    public InsufficientDataException(String message) {
        super(message);
    }
    public InsufficientDataException(String message, Throwable cause) {
      super(message, cause);
    }
}
