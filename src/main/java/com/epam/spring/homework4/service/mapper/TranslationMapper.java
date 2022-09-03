package com.epam.spring.homework4.service.mapper;

import com.epam.spring.homework4.controller.dto.TranslationDto;
import com.epam.spring.homework4.service.model.Translation;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TranslationMapper {

    TranslationMapper INSTANCE = Mappers.getMapper(TranslationMapper.class);

    TranslationDto mapTranslationDto(Translation translation);

    Translation mapTranslation(TranslationDto translationDto);

}
