package com.epam.spring.homework4.service.exception;

public class UserActivityNotFoundException extends NotFoundException {

    private static final String MESSAGE = "UserActivity was not found!";

    public UserActivityNotFoundException() {
        super(MESSAGE);
    }

}
