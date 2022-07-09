package com.epam.spring.homework5.controller.dto;

import com.epam.spring.homework5.controller.dto.validation.EnumValidator;
import com.epam.spring.homework5.controller.dto.validation.group.OnCreate;
import com.epam.spring.homework5.controller.dto.validation.group.OnUpdate;
import com.epam.spring.homework5.service.model.enums.UserHasActivityStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.sql.Timestamp;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserHasActivityDto {

    int id;

    @NotNull(message = "{userHasActivity.user.notNull}", groups = OnCreate.class)
    @Null(message = "{userHasActivity.user.null}", groups = OnUpdate.class)
    UserDto user;

    @NotNull(message = "{userHasActivity.activity.notNull}", groups = OnCreate.class)
    @Null(message = "{userHasActivity.activity.null}", groups = OnUpdate.class)
    ActivityDto activity;

    @EnumValidator(name = "{userHasActivity.status.name}",
            enumClass = UserHasActivityStatus.class)
    @Null(message = "{userHasActivity.status.null}", groups = OnCreate.class)
    @NotNull(message = "{userHasActivity.status.notNull}", groups = OnUpdate.class)
    String status;

    @Null(message = "{userHasActivity.startTime.null}")
    Timestamp startTime;

    @Null(message = "{userHasActivity.endTime.null}")
    Timestamp endTime;

    @Null(message = "{userHasActivity.creationDate.null}")
    Timestamp creationDate;

    @Null(message = "{userHasActivity.timeSpent.null}")
    String timeSpent;

}
