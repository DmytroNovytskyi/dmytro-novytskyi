package com.epam.spring.homework5.service.impl;

import com.epam.spring.homework5.controller.dto.CategoryDto;
import com.epam.spring.homework5.service.CategoryService;
import com.epam.spring.homework5.service.exception.CategoryNotFoundException;
import com.epam.spring.homework5.service.exception.TranslationAlreadyExistsException;
import com.epam.spring.homework5.service.mapper.CategoryMapper;
import com.epam.spring.homework5.service.model.Category;
import com.epam.spring.homework5.service.model.Translation;
import com.epam.spring.homework5.service.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public Page<CategoryDto> getAll(int page, int size, String sortBy, String order) {
        log.info("reading categories");
        Pageable pageable = PageRequest.of(page, size,
                order.equals("desc") ? Sort.by(sortBy).descending()
                        : Sort.by(sortBy).ascending());
        Page<CategoryDto> categories = categoryRepository.findAll(pageable)
                .map(CategoryMapper.INSTANCE::mapCategoryDto);
        log.info("categories were successfully read");
        return categories;
    }

    @Override
    @Transactional
    public CategoryDto create(CategoryDto category) {
        log.info("creating category with name:{}", category.getTranslations());
        Category mappedCategory = mapAndSetTranslationsCategory(category);
        translationPresenceCheck(mappedCategory);
        Category createdCategory = categoryRepository.save(mappedCategory);
        log.info("category with name:{} was successfully created with id:{}",
                createdCategory.getTranslations(), createdCategory.getId());
        return CategoryMapper.INSTANCE.mapCategoryDto(createdCategory);
    }

    @Override
    @Transactional
    public CategoryDto update(CategoryDto category) {
        log.info("updating category with id:{}", category.getId());
        Optional<Category> categoryOptional = categoryRepository.findById(category.getId());
        if (categoryOptional.isEmpty()) {
            throw new CategoryNotFoundException();
        }
        Category mappedCategory = mapAndSetTranslationsCategory(category);
        translationPresenceCheck(mappedCategory);
        Category persistedCategory = categoryOptional.get();
        CategoryMapper.INSTANCE.mapPresentFields(persistedCategory, mappedCategory);
        Category updatedCategory = categoryRepository.save(persistedCategory);
        log.info("category with id:{} was successfully updated", updatedCategory.getId());
        return CategoryMapper.INSTANCE.mapCategoryDto(updatedCategory);
    }

    @Override
    @Transactional
    public void deleteById(int categoryId) {
        log.info("deleting category with id:{}", categoryId);
        if (!categoryRepository.existsById(categoryId)) {
            throw new CategoryNotFoundException();
        }
        categoryRepository.deleteById(categoryId);
        log.info("category with id:{} was successfully deleted", categoryId);
    }

    private Category mapAndSetTranslationsCategory(CategoryDto category) {
        Category mappedCategory = CategoryMapper.INSTANCE.mapCategory(category);
        List<Translation> translations = mappedCategory.getTranslations();
        if (translations != null) {
            translations.forEach(t -> t.setCategory(mappedCategory));
        }
        return mappedCategory;
    }

    private void translationPresenceCheck(Category category) {
        for (Translation translation :
                category.getTranslations()) {
            if (categoryRepository.existsTranslation(translation.getName())) {
                throw new TranslationAlreadyExistsException();
            }
        }
    }

}
