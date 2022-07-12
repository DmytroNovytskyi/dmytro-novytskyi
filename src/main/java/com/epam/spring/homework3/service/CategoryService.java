package com.epam.spring.homework3.service;

import com.epam.spring.homework3.controller.dto.CategoryDto;

import java.util.List;

public interface CategoryService {

    List<CategoryDto> getAll();

    CategoryDto create(CategoryDto category);

    CategoryDto update(CategoryDto category);

    void delete(int categoryId);

}
