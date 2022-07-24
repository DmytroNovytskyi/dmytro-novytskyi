package com.epam.spring.homework6.service.exception;

public class ActiveUserHasActivityException extends ValidationException {

    private static final String message = "Request for this user and activity is already active!";

    public ActiveUserHasActivityException() {
        super(message);
    }

}
