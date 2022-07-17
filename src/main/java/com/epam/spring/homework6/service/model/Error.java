package com.epam.spring.homework6.service.model;

import com.epam.spring.homework6.service.model.enums.ErrorType;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class Error {

    String message;
    ErrorType errorType;
    LocalDateTime timeStamp;

}
