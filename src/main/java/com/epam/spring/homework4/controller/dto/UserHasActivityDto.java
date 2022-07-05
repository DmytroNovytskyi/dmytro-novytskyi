package com.epam.spring.homework4.controller.dto;

import com.epam.spring.homework4.controller.dto.validation.EnumValidator;
import com.epam.spring.homework4.controller.dto.validation.group.OnCreate;
import com.epam.spring.homework4.controller.dto.validation.group.OnUpdate;
import com.epam.spring.homework4.service.model.enums.UserHasActivityStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.sql.Timestamp;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserHasActivityDto {

    int id;

    @NotNull(message = "user should not be null", groups = OnCreate.class)
    @Null(message = "user should be null", groups = OnUpdate.class)
    UserDto user;

    @NotNull(message = "activity should not be null", groups = OnCreate.class)
    @Null(message = "activity should be null", groups = OnUpdate.class)
    ActivityDto activity;

    @EnumValidator(name = "status", enumClass = UserHasActivityStatus.class)
    @Null(message = "status should be null", groups = OnCreate.class)
    @NotNull(message = "status should not be null", groups = OnUpdate.class)
    String status;

    @Null(message = "startTime should be null")
    Timestamp startTime;

    @Null(message = "endTime should be null")
    Timestamp endTime;

    @Null(message = "creationDate should be null")
    Timestamp creationDate;

    @Null(message = "timeSpent should be null")
    String timeSpent;

}
