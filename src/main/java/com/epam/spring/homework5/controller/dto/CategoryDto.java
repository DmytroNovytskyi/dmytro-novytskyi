package com.epam.spring.homework5.controller.dto;

import com.epam.spring.homework5.controller.dto.validation.EnumValidator;
import com.epam.spring.homework5.controller.dto.validation.group.OnCreate;
import com.epam.spring.homework5.controller.dto.validation.group.OnUpdate;
import com.epam.spring.homework5.service.model.enums.CategoryStatus;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.util.List;

@Data
public class CategoryDto {

    @Null(message = "{category.id.null}", groups = OnCreate.class)
    @NotNull(message = "{category.id.notNull}", groups = OnUpdate.class)
    Integer id;

    @EnumValidator(name = "{category.status.name}", enumClass = CategoryStatus.class)
    @NotNull(message = "{category.status.notNull}", groups = OnCreate.class)
    String status;

    @NotNull(message = "{category.translations.notNull}", groups = OnCreate.class)
    @NotEmpty(message = "{category.translations.notEmpty}")
    List<TranslationDto> translations;

}
