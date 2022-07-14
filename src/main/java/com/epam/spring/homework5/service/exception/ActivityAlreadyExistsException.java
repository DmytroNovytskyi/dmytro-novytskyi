package com.epam.spring.homework5.service.exception;

public class ActivityAlreadyExistsException extends ValidationException {

    private static final String MESSAGE = "Activity with this name already exists!";

    public ActivityAlreadyExistsException() {
        super(MESSAGE);
    }

}
