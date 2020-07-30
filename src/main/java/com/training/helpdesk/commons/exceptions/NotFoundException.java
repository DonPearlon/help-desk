package com.training.helpdesk.commons.exceptions;

/**
 * Defines an exception that can be thrown when requested element was not found in
 * database.
 *
 * @author Alexandr_Terehov
 */
public class NotFoundException extends RuntimeException {
    public static final String DEFAULT_MESSAGE = "Resource not found";
    private final String message;

    public NotFoundException(String message) {
        this.message = message;
    }

    public NotFoundException() {
        this.message = DEFAULT_MESSAGE;
    }

    @Override
    public String getMessage() {
        return message;
    }
}