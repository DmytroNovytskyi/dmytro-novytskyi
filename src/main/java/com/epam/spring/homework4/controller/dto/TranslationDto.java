package com.epam.spring.homework4.controller.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
public class TranslationDto {

    int id;

    @Pattern(message = "lang is not valid(^[a-z]{2}$)", regexp = "^[a-z]{2}$")
    @NotNull(message = "lang should not be null")
    String lang;

    @Pattern(message = "translation is not valid((^[\\sa-zA-Z0-9/.-]{8,45}$)|(^[\\sА-ЩЬЮЯҐЄІЇа-щьюяґєії0-9/.-]{8,45}$))",
            regexp = "(^[\\sa-zA-Z0-9/.-]{8,45}$)|(^[\\sА-ЩЬЮЯҐЄІЇа-щьюяґєії0-9/.-]{8,45}$)")
    @NotNull(message = "translation should not be null")
    String name;

}
