package com.epam.spring.homework3.controller.dto;

import com.epam.spring.homework3.service.model.enums.UserHasActivityStatus;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class UserHasActivityDto {

    int id;
    UserDto user;
    ActivityDto activity;
    UserHasActivityStatus status;
    Timestamp startTime;
    Timestamp endTime;
    Timestamp creationDate;
    String timeSpent;

}
