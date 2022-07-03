package com.epam.spring.timekeeper.controller.dto;

import com.epam.spring.timekeeper.service.model.enums.ActivityStatus;
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
