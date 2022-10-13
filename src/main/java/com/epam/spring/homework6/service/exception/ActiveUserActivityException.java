package com.epam.spring.homework6.service.exception;

public class ActiveUserActivityException extends ValidationException {

    private static final String message = "Request for this user and activity is already active!";

    public ActiveUserActivityException() {
        super(message);
    }

}
