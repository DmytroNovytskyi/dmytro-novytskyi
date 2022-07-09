package com.epam.spring.homework5.service.exception;

public class CategoryNotFoundException extends NotFoundException {

    private static final String MESSAGE = "Category was not found!";

    public CategoryNotFoundException() {
        super(MESSAGE);
    }

}
