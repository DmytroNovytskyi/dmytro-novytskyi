package com.epam.spring.homework4.service.mapper;

import com.epam.spring.homework4.controller.dto.CategoryDto;
import com.epam.spring.homework4.service.model.Category;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

@Mapper(uses = TranslationMapper.class,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface CategoryMapper {

    CategoryMapper INSTANCE = Mappers.getMapper(CategoryMapper.class);

    CategoryDto mapCategoryDto(Category category);

    Category mapCategory(CategoryDto categoryDto);

    void mapPresentFields(@MappingTarget Category category, CategoryDto categoryDto);

}
