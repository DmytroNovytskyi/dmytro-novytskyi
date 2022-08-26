package com.epam.spring.homework3.service.model;

import com.epam.spring.homework3.service.model.enums.UserHasActivityStatus;
import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;

@Data
@Builder
public class UserHasActivity {

    private int id;
    private User user;
    private Activity activity;
    private UserHasActivityStatus status;
    private Timestamp startTime;
    private Timestamp endTime;
    private Timestamp creationDate;

}
