package com.epam.spring.homework4.controller.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
public class TranslationDto {

    private int id;

    @Pattern(message = "{translation.lang.pattern}", regexp = "^[a-z]{2}$")
    @NotNull(message = "{translation.lang.notNull}")
    private String lang;

    @Pattern(message = "{translation.name.pattern}",
            regexp = "(^[\\sa-zA-Z0-9/.-]{8,45}$)|(^[\\sА-ЩЬЮЯҐЄІЇа-щьюяґєії0-9/.-]{8,45}$)")
    @NotNull(message = "{translation.name.notNull}")
    private String name;

}
