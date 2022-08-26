package com.epam.spring.homework3.controller.dto;

import com.epam.spring.homework3.service.model.enums.ActivityStatus;
import lombok.Data;

@Data
public class ActivityDto {

    private int id;
    private CategoryDto category;
    private String name;
    private ActivityStatus status;
    private int userCount;
    private String description;

}
