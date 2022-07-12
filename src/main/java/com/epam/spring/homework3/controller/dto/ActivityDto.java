package com.epam.spring.homework3.controller.dto;

import com.epam.spring.homework3.service.model.enums.ActivityStatus;
import lombok.Data;

@Data
public class ActivityDto {

    int id;
    CategoryDto category;
    String name;
    ActivityStatus status;
    int userCount;
    String description;

}
