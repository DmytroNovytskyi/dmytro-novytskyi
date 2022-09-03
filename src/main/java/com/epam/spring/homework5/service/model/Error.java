package com.epam.spring.homework5.service.model;

import com.epam.spring.homework5.service.model.enums.ErrorType;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class Error {

    private String message;
    private ErrorType errorType;
    private LocalDateTime timeStamp;

}
