package com.epam.spring.homework3.service.mapper;

import com.epam.spring.homework3.controller.dto.TranslationDto;
import com.epam.spring.homework3.service.model.Translation;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TranslationMapper {

    TranslationMapper INSTANCE = Mappers.getMapper(TranslationMapper.class);

    TranslationDto mapTranslationDto(Translation translation);

    Translation mapTranslation(TranslationDto translationDto);

}
