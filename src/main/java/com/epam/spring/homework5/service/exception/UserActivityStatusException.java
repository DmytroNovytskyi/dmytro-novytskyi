package com.epam.spring.homework5.service.exception;

public class UserActivityStatusException extends ValidationException {

    private static final String MESSAGE = "Wrong status for userActivity!";

    public UserActivityStatusException() {
        super(MESSAGE);
    }

}
