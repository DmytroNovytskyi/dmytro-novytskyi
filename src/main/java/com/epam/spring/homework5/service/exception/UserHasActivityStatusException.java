package com.epam.spring.homework5.service.exception;

public class UserHasActivityStatusException extends ValidationException {

    private static final String MESSAGE = "Wrong status for userHasActivity!";

    public UserHasActivityStatusException() {
        super(MESSAGE);
    }

}
