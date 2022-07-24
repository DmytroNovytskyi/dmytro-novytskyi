package com.epam.spring.homework6.service;

import com.epam.spring.homework6.controller.dto.CategoryDto;
import org.springframework.data.domain.Page;

public interface CategoryService {

    Page<CategoryDto> getAll(int page, int size, String sortBy, String order);

    CategoryDto create(CategoryDto category);

    CategoryDto update(CategoryDto category);

    void delete(int categoryId);

}
