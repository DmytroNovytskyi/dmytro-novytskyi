package com.epam.spring.homework4.controller.dto;

import com.epam.spring.homework4.service.model.enums.CategoryStatus;
import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class CategoryDto {

    int id;
    CategoryStatus status;
    Set<TranslationDto> translations;

}
