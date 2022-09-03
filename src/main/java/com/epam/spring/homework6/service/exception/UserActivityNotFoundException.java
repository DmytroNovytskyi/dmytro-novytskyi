package com.epam.spring.homework6.service.exception;

public class UserActivityNotFoundException extends ValidationException {

    private static final String MESSAGE = "UserActivity was not found!";

    public UserActivityNotFoundException() {
        super(MESSAGE);
    }

}
