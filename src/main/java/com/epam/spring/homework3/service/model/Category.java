package com.epam.spring.homework3.service.model;

import com.epam.spring.homework3.service.model.enums.CategoryStatus;
import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class Category {

    private int id;
    private CategoryStatus status;
    private Set<Translation> translations;

}
