package com.epam.spring.homework6.service.exception;

public class UserAlreadyExistsException extends ValidationException {

    private static final String MESSAGE = "User with this username or email already exists!";

    public UserAlreadyExistsException() {
        super(MESSAGE);
    }

}
