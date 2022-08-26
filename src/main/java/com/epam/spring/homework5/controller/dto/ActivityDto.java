package com.epam.spring.homework5.controller.dto;

import com.epam.spring.homework5.controller.dto.validation.EnumValidator;
import com.epam.spring.homework5.controller.dto.validation.group.OnCreate;
import com.epam.spring.homework5.controller.dto.validation.group.OnUpdate;
import com.epam.spring.homework5.service.model.enums.ActivityStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Pattern;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ActivityDto {

    @Null(message = "{activity.id.null}", groups = OnCreate.class)
    @NotNull(message = "{activity.id.notNull}", groups = OnUpdate.class)
    private Integer id;

    @NotNull(message = "{activity.category.notNull}", groups = OnCreate.class)
    private CategoryDto category;

    @Pattern(message = "{activity.name.pattern}", regexp = "^[\\sa-zA-Z0-9/.-]{8,45}$")
    @NotNull(message = "{activity.name.notNull}", groups = OnCreate.class)
    private String name;

    @EnumValidator(name = "{activity.name.status.name}", enumClass = ActivityStatus.class)
    @NotNull(message = "{activity.status.notNull}", groups = OnCreate.class)
    private String status;

    @Null(message = "{activity.userCount.null}")
    private Integer userCount;

    @Pattern(message = "{activity.description.pattern}", regexp = "^.{0,255}$")
    private String description;

}
