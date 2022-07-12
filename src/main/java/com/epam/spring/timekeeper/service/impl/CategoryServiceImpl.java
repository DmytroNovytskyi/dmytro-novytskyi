package com.epam.spring.timekeeper.service.impl;


import com.epam.spring.timekeeper.controller.dto.CategoryDto;
import com.epam.spring.timekeeper.service.CategoryService;
import com.epam.spring.timekeeper.service.exception.NotFoundException;
import com.epam.spring.timekeeper.service.mapper.CategoryMapper;
import com.epam.spring.timekeeper.service.model.Category;
import com.epam.spring.timekeeper.service.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public List<CategoryDto> getAll() {
        log.info("reading all categories");
        return categoryRepository.findAll().stream()
                .map(CategoryMapper.INSTANCE::mapCategoryDto)
                .sorted(Comparator.comparing(CategoryDto::getId))
                .toList();
    }

    @Override
    public CategoryDto create(CategoryDto category) {
        log.info("creating category with name:{}", category.getTranslations());
        Category entity = CategoryMapper.INSTANCE.mapCategory(category);
        return CategoryMapper.INSTANCE.mapCategoryDto(categoryRepository.create(entity));
    }

    @Override
    public CategoryDto update(CategoryDto category) {
        log.info("updating category with id:{}", category.getId());
        if (categoryRepository.findById(category.getId()) == null) {
            throw new NotFoundException("No category with id=" + category.getId() + " was found to update.");
        }
        Category entity = CategoryMapper.INSTANCE.mapCategory(category);
        return CategoryMapper.INSTANCE.mapCategoryDto(categoryRepository.update(entity));
    }

    @Override
    public void delete(int categoryId) {
        log.info("deleting category with id:{}", categoryId);
        if (categoryRepository.findById(categoryId) == null) {
            throw new NotFoundException("No category with id=" + categoryId + " was found to delete.");
        }
        categoryRepository.deleteById(categoryId);
    }

}
