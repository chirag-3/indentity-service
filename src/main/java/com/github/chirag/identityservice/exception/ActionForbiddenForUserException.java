package com.github.chirag.identityservice.exception;

public class ActionForbiddenForUserException extends RuntimeException {
    public ActionForbiddenForUserException(String message) {
        super(message);
    }

    public ActionForbiddenForUserException(String message, Throwable cause) {
        super(message, cause);
    }
}
