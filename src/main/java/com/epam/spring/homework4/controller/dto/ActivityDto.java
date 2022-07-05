package com.epam.spring.homework4.controller.dto;

import com.epam.spring.homework4.service.model.enums.ActivityStatus;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ActivityDto {

    int id;
    CategoryDto category;
    String name;
    ActivityStatus status;
    int userCount;
    String description;

}
