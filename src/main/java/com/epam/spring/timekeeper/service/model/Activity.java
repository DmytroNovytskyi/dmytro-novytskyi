package com.epam.spring.timekeeper.service.model;

import com.epam.spring.timekeeper.service.model.enums.ActivityStatus;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Activity {

    int id;
    Category category;
    String name;
    ActivityStatus status;
    String description;

}
