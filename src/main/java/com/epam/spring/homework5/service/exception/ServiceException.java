package com.epam.spring.homework5.service.exception;

import com.epam.spring.homework5.service.model.enums.ErrorType;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public abstract class ServiceException extends RuntimeException {

    ErrorType errorType;

    public ServiceException(String message, ErrorType errorType) {
        super(message);
        this.errorType = errorType;
    }

}
