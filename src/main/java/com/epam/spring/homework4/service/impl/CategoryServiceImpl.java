package com.epam.spring.homework4.service.impl;

import com.epam.spring.homework4.controller.dto.CategoryDto;
import com.epam.spring.homework4.service.CategoryService;
import com.epam.spring.homework4.service.exception.NotFoundException;
import com.epam.spring.homework4.service.mapper.CategoryMapper;
import com.epam.spring.homework4.service.model.Category;
import com.epam.spring.homework4.service.repository.CategoryRepository;
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
        Category stored = categoryRepository.findById(category.getId());
        if (stored == null) {
            throw new NotFoundException("No category with id=" + category.getId() + " was found to update.");
        }
        CategoryMapper.INSTANCE.mapPresentFields(stored, category);
        return CategoryMapper.INSTANCE.mapCategoryDto(categoryRepository.update(stored));
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
