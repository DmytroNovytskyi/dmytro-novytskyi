package com.epam.spring.homework5.controller.dto;

import com.epam.spring.homework5.controller.dto.validation.EnumValidator;
import com.epam.spring.homework5.controller.dto.validation.group.OnCreate;
import com.epam.spring.homework5.service.model.enums.ActivityStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ActivityDto {

    int id;

    @NotNull(message = "{activity.category.notNull}", groups = OnCreate.class)
    CategoryDto category;

    @Pattern(message = "{activity.name.pattern}", regexp = "^[\\sa-zA-Z0-9/.-]{8,45}$")
    @NotNull(message = "{activity.name.notNull}", groups = OnCreate.class)
    String name;

    @EnumValidator(name = "{activity.name.status.name}", enumClass = ActivityStatus.class)
    @NotNull(message = "{activity.status.notNull}", groups = OnCreate.class)
    String status;

    int userCount;

    @Pattern(message = "{activity.description.pattern}", regexp = "^.{0,255}$")
    String description;

}
