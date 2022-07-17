package com.epam.spring.homework6.service.exception;

import com.epam.spring.homework6.service.model.enums.ErrorType;

public class TranslationsMappingException extends ServiceException {

    private static final String message = "Unknown error occurred while updating translations!";

    public TranslationsMappingException() {
        super(message, ErrorType.FATAL_ERROR_TYPE);
    }

}
