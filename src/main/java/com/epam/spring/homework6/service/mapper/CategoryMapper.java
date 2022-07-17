package com.epam.spring.homework6.service.mapper;

import com.epam.spring.homework6.controller.dto.CategoryDto;
import com.epam.spring.homework6.service.exception.TranslationsMappingException;
import com.epam.spring.homework6.service.model.Category;
import com.epam.spring.homework6.service.model.Translation;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(uses = TranslationMapper.class,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface CategoryMapper {

    CategoryMapper INSTANCE = Mappers.getMapper(CategoryMapper.class);

    CategoryDto mapCategoryDto(Category category);

    @Mapping(target = "id", defaultValue = "0")
    Category mapCategory(CategoryDto categoryDto);

    @Mapping(source = "translations", target = "translations", qualifiedByName = "mapTranslationsWithoutId")
    void mapPresentFields(@MappingTarget Category toCategory, Category fromCategory);

    @Named("mapTranslationsWithoutId")
    default void mapTranslationsWithoutId(@MappingTarget List<Translation> toTranslations,
                                          List<Translation> fromTranslations) {
        List<String> presentLang = toTranslations.stream()
                .map(Translation::getLang)
                .toList();
        List<String> allLang = fromTranslations.stream()
                .map(Translation::getLang)
                .toList();
        toTranslations.removeIf(t -> !allLang.contains(t.getLang()));
        toTranslations.forEach(toTranslation -> toTranslation.setName(fromTranslations.stream()
                .filter(fromTranslation -> fromTranslation.getLang().equals(toTranslation.getLang()))
                .findFirst().orElseThrow(TranslationsMappingException::new).getName()));
        fromTranslations.removeIf(t -> presentLang.contains(t.getLang()));
        toTranslations.addAll(fromTranslations);
    }

}
