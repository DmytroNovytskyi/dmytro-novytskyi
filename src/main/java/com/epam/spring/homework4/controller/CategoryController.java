package com.epam.spring.homework4.controller;

import com.epam.spring.homework4.controller.api.CategoryApi;
import com.epam.spring.homework4.controller.dto.CategoryDto;
import com.epam.spring.homework4.service.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class CategoryController implements CategoryApi {

    private final CategoryService categoryService;

    @Override
    public List<CategoryDto> getAllCategories() {
        log.info("accepted request to get all categories");
        return categoryService.getAll();
    }

    @Override
    public CategoryDto createCategory(CategoryDto category) {
        log.info("accepted request to create category with name:{}", category.getTranslations());
        return categoryService.create(category);
    }

    @Override
    public CategoryDto updateCategory(CategoryDto category) {
        log.info("accepted request to update category with id:{}", category.getId());
        return categoryService.update(category);
    }

    @Override
    public ResponseEntity<Void> deleteCategory(int categoryId) {
        log.info("accepted request to delete category with id:{}", categoryId);
        categoryService.delete(categoryId);
        return ResponseEntity.noContent().build();
    }

}
