package com.epam.spring.homework6.service.exception;

public class ActivityNotFoundException extends ValidationException {

    private static final String MESSAGE = "Activity was not found!";

    public ActivityNotFoundException() {
        super(MESSAGE);
    }

}
