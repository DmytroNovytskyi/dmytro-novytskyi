package com.epam.spring.homework4.controller.dto;

import com.epam.spring.homework4.controller.dto.validation.EnumValidator;
import com.epam.spring.homework4.controller.dto.validation.group.OnCreate;
import com.epam.spring.homework4.controller.dto.validation.group.OnUpdate;
import com.epam.spring.homework4.service.model.enums.UserActivityStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.sql.Timestamp;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserActivityDto {

    private int id;

    @NotNull(message = "{userActivity.user.notNull}", groups = OnCreate.class)
    @Null(message = "{userActivity.user.null}", groups = OnUpdate.class)
    private UserDto user;

    @NotNull(message = "{userActivity.activity.notNull}", groups = OnCreate.class)
    @Null(message = "{userActivity.activity.null}", groups = OnUpdate.class)
    private ActivityDto activity;

    @EnumValidator(name = "{userActivity.status.name}",
            enumClass = UserActivityStatus.class)
    @Null(message = "{userActivity.status.null}", groups = OnCreate.class)
    @NotNull(message = "{userActivity.status.notNull}", groups = OnUpdate.class)
    private String status;

    @Null(message = "{userActivity.startTime.null}")
    private Timestamp startTime;

    @Null(message = "{userActivity.endTime.null}")
    private Timestamp endTime;

    @Null(message = "{userActivity.creationDate.null}")
    private Timestamp creationDate;

    @Null(message = "{userActivity.timeSpent.null}")
    private String timeSpent;

}
