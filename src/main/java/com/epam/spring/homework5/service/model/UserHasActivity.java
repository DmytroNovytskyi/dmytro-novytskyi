package com.epam.spring.homework5.service.model;

import com.epam.spring.homework5.service.model.enums.UserHasActivityStatus;
import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;

@Data
@Builder
public class UserHasActivity {

    int id;
    User user;
    Activity activity;
    UserHasActivityStatus status;
    Timestamp startTime;
    Timestamp endTime;
    Timestamp creationDate;

}
