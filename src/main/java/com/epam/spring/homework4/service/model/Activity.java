package com.epam.spring.homework4.service.model;

import com.epam.spring.homework4.service.model.enums.ActivityStatus;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Activity {

    private int id;
    private Category category;
    private String name;
    private ActivityStatus status;
    private String description;

}
