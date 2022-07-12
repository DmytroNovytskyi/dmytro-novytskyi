package com.epam.spring.homework3.service.mapper;

import com.epam.spring.homework3.controller.dto.CategoryDto;
import com.epam.spring.homework3.service.model.Category;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(uses = TranslationMapper.class)
public interface CategoryMapper {

    CategoryMapper INSTANCE = Mappers.getMapper(CategoryMapper.class);

    CategoryDto mapCategoryDto(Category category);

    Category mapCategory(CategoryDto categoryDto);

}
