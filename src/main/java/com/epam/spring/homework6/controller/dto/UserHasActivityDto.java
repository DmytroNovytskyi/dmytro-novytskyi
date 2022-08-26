package com.epam.spring.homework6.controller.dto;

import com.epam.spring.homework6.controller.dto.validation.EnumValidator;
import com.epam.spring.homework6.controller.dto.validation.group.OnCreate;
import com.epam.spring.homework6.controller.dto.validation.group.OnUpdate;
import com.epam.spring.homework6.service.model.enums.UserHasActivityStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.sql.Timestamp;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserHasActivityDto {

    @Null(message = "{userHasActivity.id.null}", groups = OnCreate.class)
    @NotNull(message = "{userHasActivity.id.notNull}", groups = OnUpdate.class)
    private Integer id;

    @NotNull(message = "{userHasActivity.user.notNull}", groups = OnCreate.class)
    @Null(message = "{userHasActivity.user.null}", groups = OnUpdate.class)
    private UserDto user;

    @NotNull(message = "{userHasActivity.activity.notNull}", groups = OnCreate.class)
    @Null(message = "{userHasActivity.activity.null}", groups = OnUpdate.class)
    private ActivityDto activity;

    @EnumValidator(name = "{userHasActivity.status.name}",
            enumClass = UserHasActivityStatus.class)
    @Null(message = "{userHasActivity.status.null}", groups = OnCreate.class)
    @NotNull(message = "{userHasActivity.status.notNull}", groups = OnUpdate.class)
    private String status;

    @Null(message = "{userHasActivity.startTime.null}")
    private Timestamp startTime;

    @Null(message = "{userHasActivity.endTime.null}")
    private Timestamp endTime;

    @Null(message = "{userHasActivity.creationDate.null}")
    private Timestamp creationDate;

    @Null(message = "{userHasActivity.timeSpent.null}")
    private String timeSpent;

}
