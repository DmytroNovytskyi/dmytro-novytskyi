package com.epam.spring.homework6.service.exception;

public class TranslationAlreadyExistsException extends ValidationException {

    private static final String MESSAGE = "Translation with this name already exists!";

    public TranslationAlreadyExistsException() {
        super(MESSAGE);
    }

}
