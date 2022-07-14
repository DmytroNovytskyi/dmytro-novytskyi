package com.epam.spring.homework5.service.mapper;

import com.epam.spring.homework5.controller.dto.TranslationDto;
import com.epam.spring.homework5.service.model.Translation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

@Mapper(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface TranslationMapper {

    TranslationMapper INSTANCE = Mappers.getMapper(TranslationMapper.class);

    TranslationDto mapTranslationDto(Translation translation);

    @Mapping(target = "id", constant = "0")
    Translation mapTranslation(TranslationDto translationDto);

}
