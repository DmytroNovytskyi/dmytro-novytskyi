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

    int id;

    @NotNull(message = "category should not be null", groups = OnCreate.class)
    CategoryDto category;

    @Pattern(message = "activity name is not valid(^[\\sa-zA-Z0-9/.-]{8,45}$)", regexp = "^[\\sa-zA-Z0-9/.-]{8,45}$")
    @NotNull(message = "name should not be blank", groups = OnCreate.class)
    String name;

    @EnumValidator(name = "status", enumClass = ActivityStatus.class)
    @NotNull(message = "status should not be null", groups = OnCreate.class)
    String status;

    int userCount;

    @Pattern(message = "activity description is not valid(^.{0,255}$)", regexp = "^.{0,255}$")
    String description;

}
