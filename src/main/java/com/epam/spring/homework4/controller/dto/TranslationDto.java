package com.epam.spring.homework4.controller.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TranslationDto {

    int id;
    String lang;
    String name;

}
