package com.training.helpdesk.commons.exceptions;

/**
 * Defines an exception that can be thrown when executing action is not valid.
 *
 * @author Alexandr_Terehov
 */
public class ActionForbiddenException extends RuntimeException {
    public static final String DEFAULT_MESSAGE = "Action is forbidden";
    private final String message;

    public ActionForbiddenException(String message) {
        this.message = message;
    }

    public ActionForbiddenException() {
        this.message = DEFAULT_MESSAGE;
    }

    @Override
    public String getMessage() {
        return message;
    }
}