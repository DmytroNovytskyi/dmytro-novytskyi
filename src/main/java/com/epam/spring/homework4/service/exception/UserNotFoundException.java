package com.epam.spring.homework4.service.exception;

public class UserNotFoundException extends NotFoundException {

    private static final String MESSAGE = "User was not found!";

    public UserNotFoundException() {
        super(MESSAGE);
    }
}
