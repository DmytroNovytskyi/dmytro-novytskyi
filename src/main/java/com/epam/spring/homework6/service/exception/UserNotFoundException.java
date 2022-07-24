package com.epam.spring.homework6.service.exception;

public class UserNotFoundException extends ValidationException {

    private static final String MESSAGE = "User was not found!";

    public UserNotFoundException() {
        super(MESSAGE);
    }
}
