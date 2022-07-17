package com.epam.spring.homework6.controller;

import com.epam.spring.homework6.controller.api.CategoryApi;
import com.epam.spring.homework6.controller.dto.CategoryDto;
import com.epam.spring.homework6.service.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class CategoryController implements CategoryApi {

    private final CategoryService categoryService;

    @Override
    public Page<CategoryDto> getSortedPagedCategories(int page, int size, String sortBy, String order) {
        log.info("accepted request to get categories");
        return categoryService.getAll(page, size, sortBy, order);
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
