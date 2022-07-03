package com.epam.spring.timekeeper.controller.dto;

import com.epam.spring.timekeeper.service.model.enums.CategoryStatus;
import lombok.Data;

import java.util.Set;

@Data
public class CategoryDto {

    int id;
    CategoryStatus status;
    Set<TranslationDto> translations;

}
