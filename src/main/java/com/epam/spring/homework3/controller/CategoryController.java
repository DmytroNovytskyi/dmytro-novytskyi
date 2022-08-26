package com.epam.spring.homework3.controller;

import com.epam.spring.homework3.controller.dto.CategoryDto;
import com.epam.spring.homework3.service.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/category")
public class CategoryController {

    private final CategoryService categoryService;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public List<CategoryDto> getAllCategories() {
        log.info("accepted request to get all categories");
        return categoryService.getAll();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public CategoryDto createCategory(@RequestBody CategoryDto category) {
        log.info("accepted request to create category with name:{}", category.getTranslations());
        return categoryService.create(category);
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping
    public CategoryDto updateCategory(@RequestBody CategoryDto category) {
        log.info("accepted request to update category with id:{}", category.getId());
        return categoryService.update(category);
    }

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<Void> deleteCategory(@PathVariable int categoryId) {
        log.info("accepted request to delete category with id:{}", categoryId);
        categoryService.deleteById(categoryId);
        return ResponseEntity.noContent().build();
    }

}
