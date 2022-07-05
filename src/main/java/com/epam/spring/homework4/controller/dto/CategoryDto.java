package com.epam.spring.homework4.controller.dto;

import com.epam.spring.homework4.controller.dto.validation.EnumValidator;
import com.epam.spring.homework4.controller.dto.validation.group.OnCreate;
import com.epam.spring.homework4.service.model.enums.CategoryStatus;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Set;

@Data
public class CategoryDto {

    int id;

    @EnumValidator(name = "status", enumClass = CategoryStatus.class)
    @NotNull(message = "status should not be null", groups = OnCreate.class)
    String status;

    @NotNull(message = "translations should not be null", groups = OnCreate.class)
    Set<TranslationDto> translations;

}
