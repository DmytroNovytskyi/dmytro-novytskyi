package com.epam.spring.homework3.service.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Translation {

    private int id;
    private int categoryId;
    private String lang;
    private String name;

}
