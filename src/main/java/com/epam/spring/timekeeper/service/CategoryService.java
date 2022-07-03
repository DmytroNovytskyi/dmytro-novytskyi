package com.epam.spring.timekeeper.service;

import com.epam.spring.timekeeper.controller.dto.CategoryDto;

import java.util.List;

public interface CategoryService {

    List<CategoryDto> getAll();

    CategoryDto create(CategoryDto category);

    CategoryDto update(CategoryDto category);

    void delete(int categoryId);

}
