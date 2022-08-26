package com.epam.spring.homework4.controller.dto;

import com.epam.spring.homework4.controller.dto.validation.EnumValidator;
import com.epam.spring.homework4.controller.dto.validation.group.OnCreate;
import com.epam.spring.homework4.service.model.enums.ActivityStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ActivityDto {

    private int id;

    @NotNull(message = "{activity.category.notNull}", groups = OnCreate.class)
    private CategoryDto category;

    @Pattern(message = "{activity.name.pattern}", regexp = "^[\\sa-zA-Z0-9/.-]{8,45}$")
    @NotNull(message = "{activity.name.notNull}", groups = OnCreate.class)
    private String name;

    @EnumValidator(name = "{activity.name.status.name}", enumClass = ActivityStatus.class)
    @NotNull(message = "{activity.status.notNull}", groups = OnCreate.class)
    private String status;

    private int userCount;

    @Pattern(message = "{activity.description.pattern}", regexp = "^.{0,255}$")
    private String description;

}
