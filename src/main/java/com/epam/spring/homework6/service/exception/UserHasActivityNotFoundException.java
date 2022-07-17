package com.epam.spring.homework6.service.exception;

public class UserHasActivityNotFoundException extends ValidationException {

    private static final String MESSAGE = "UserHasActivity was not found!";

    public UserHasActivityNotFoundException() {
        super(MESSAGE);
    }

}
