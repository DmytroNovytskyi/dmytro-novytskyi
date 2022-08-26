package com.epam.spring.homework3.controller.dto;

import com.epam.spring.homework3.service.model.enums.UserHasActivityStatus;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class UserHasActivityDto {

    private int id;
    private UserDto user;
    private ActivityDto activity;
    private UserHasActivityStatus status;
    private Timestamp startTime;
    private Timestamp endTime;
    private Timestamp creationDate;
    private String timeSpent;

}
