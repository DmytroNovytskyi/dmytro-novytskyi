package com.epam.spring.homework4.service.exception;

public class ActivityNotFoundException extends NotFoundException {

    private static final String MESSAGE = "Activity was not found!";

    public ActivityNotFoundException() {
        super(MESSAGE);
    }

}
