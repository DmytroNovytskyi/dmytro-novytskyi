package com.epam.spring.homework4.service.model;

import com.epam.spring.homework4.service.model.enums.CategoryStatus;
import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class Category {

    int id;
    CategoryStatus status;
    Set<Translation> translations;

}
