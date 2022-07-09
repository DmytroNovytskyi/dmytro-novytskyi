package com.epam.spring.homework5.service.exception;

public class UserHasActivityNotFoundException extends NotFoundException {

    private static final String MESSAGE = "UserHasActivity was not found!";

    public UserHasActivityNotFoundException() {
        super(MESSAGE);
    }

}
