package com.epam.spring.homework5.service.exception;

import com.epam.spring.homework5.service.model.enums.ErrorType;

public abstract class ValidationException extends ServiceException {

    public ValidationException(String message) {
        super(message, ErrorType.VALIDATION_ERROR_TYPE);
    }

}
