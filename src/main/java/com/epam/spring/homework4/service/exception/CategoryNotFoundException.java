package com.epam.spring.homework4.service.exception;

public class CategoryNotFoundException extends NotFoundException {

    private static final String MESSAGE = "Category was not found!";

    public CategoryNotFoundException() {
        super(MESSAGE);
    }

}
