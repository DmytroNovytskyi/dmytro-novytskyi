package com.epam.spring.homework6.service.exception;

public class CategoryNotFoundException extends ValidationException {

    private static final String MESSAGE = "Category was not found!";

    public CategoryNotFoundException() {
        super(MESSAGE);
    }

}
