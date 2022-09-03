package com.epam.spring.homework4.service.model;

import com.epam.spring.homework4.service.model.enums.UserActivityStatus;
import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;

@Data
@Builder
public class UserActivity {

    private int id;
    private User user;
    private Activity activity;
    private UserActivityStatus status;
    private Timestamp startTime;
    private Timestamp endTime;
    private Timestamp creationDate;

}
