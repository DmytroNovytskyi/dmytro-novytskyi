package com.epam.spring.homework3.controller.dto;

import com.epam.spring.homework3.service.model.enums.CategoryStatus;
import lombok.Data;

import java.util.Set;

@Data
public class CategoryDto {

    int id;
    CategoryStatus status;
    Set<TranslationDto> translations;

}
