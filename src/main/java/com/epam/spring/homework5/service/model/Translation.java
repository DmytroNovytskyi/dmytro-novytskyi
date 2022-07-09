package com.epam.spring.homework5.service.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Translation {

    int id;
    int categoryId;
    String lang;
    String name;

}
