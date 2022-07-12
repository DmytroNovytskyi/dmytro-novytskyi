package com.epam.spring.homework4.service.exception;

import com.epam.spring.homework4.service.model.enums.ErrorType;

public abstract class NotFoundException extends ServiceException {

    public NotFoundException(String message) {
        super(message, ErrorType.VALIDATION_ERROR_TYPE);
    }

}
