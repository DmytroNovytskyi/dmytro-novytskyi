package com.epam.spring.timekeeper.controller.dto;

import com.epam.spring.timekeeper.service.model.enums.UserHasActivityStatus;
import com.epam.spring.timekeeper.service.model.Activity;
import com.epam.spring.timekeeper.service.model.User;
import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;

@Data
@Builder
public class UserHasActivityDto {

    int id;
    User user;
    Activity activity;
    UserHasActivityStatus status;
    Timestamp startTime;
    Timestamp endTime;
    Timestamp creationDate;
    String timeSpent;

}
