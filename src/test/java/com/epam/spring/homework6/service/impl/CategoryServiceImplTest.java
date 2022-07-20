package com.epam.spring.homework6.service.impl;

import com.epam.spring.homework6.controller.dto.CategoryDto;
import com.epam.spring.homework6.service.exception.CategoryNotFoundException;
import com.epam.spring.homework6.service.exception.TranslationAlreadyExistsException;
import com.epam.spring.homework6.service.mapper.CategoryMapper;
import com.epam.spring.homework6.service.model.Category;
import com.epam.spring.homework6.service.repository.CategoryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static com.epam.spring.homework6.util.CategoryTestData.*;
import static com.epam.spring.homework6.util.CommonTestData.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceImplTest {

    @InjectMocks
    private CategoryServiceImpl categoryService;

    @Mock
    private CategoryRepository categoryRepository;

    @Test
    void givenPageableData_whenFindAll_thenReturnPageOfCategories() {
        Pageable pageable = createPageable();
        List<Category> categories = createCategoryList();
        Page<Category> activityPage = new PageImpl<>(categories, pageable, categories.size());
        Page<CategoryDto> expectedPage = activityPage.map(CategoryMapper.INSTANCE::mapCategoryDto);

        when(categoryRepository.findAll(pageable)).thenReturn(activityPage);

        Page<CategoryDto> actualPage = categoryService.getAll(PAGE, SIZE, SORT_BY, ORDER);

        assertThat(actualPage.getSize(), is(SIZE));
        assertThat(actualPage.getPageable(), is(pageable));
        assertThat(actualPage, is(expectedPage));
        verify(categoryRepository, times(1)).findAll(pageable);
    }

    @Test
    void givenCategoryDtoWithExistingTranslation_whenCreate_thenThrowTranslationAlreadyExistsException() {
        CategoryDto categoryDto = createCategoryDto();

        when(categoryRepository.existsTranslation(NAME_EN)).thenReturn(false);
        when(categoryRepository.existsTranslation(NAME_UA)).thenReturn(true);

        assertThatExceptionOfType(TranslationAlreadyExistsException.class)
                .isThrownBy(() -> categoryService.create(categoryDto))
                .withMessage("Translation with this name already exists!");
        verify(categoryRepository, times(1)).existsTranslation(NAME_EN);
        verify(categoryRepository, times(1)).existsTranslation(NAME_UA);
        verify(categoryRepository, times(0)).save(any());
    }

    @Test
    void givenValidCategoryDto_whenCreate_thenReturnCreatedCategoryDto() {
        CategoryDto categoryDto = createCategoryDto();
        Category category = createCategory();

        when(categoryRepository.save(CategoryMapper.INSTANCE.mapCategory(categoryDto)))
                .thenReturn(category);

        CategoryDto actualCategoryDto = categoryService.create(categoryDto);
        CategoryDto expectedCategoryDto = createCategoryDto();
        expectedCategoryDto.setId(category.getId());

        assertThat(actualCategoryDto, is(expectedCategoryDto));
        verify(categoryRepository, times(1))
                .save(CategoryMapper.INSTANCE.mapCategory(categoryDto));
    }

    @Test
    void givenCategoryDtoWithInvalidId_whenUpdate_thenThrowCategoryNotFoundException() {
        CategoryDto categoryDto = createCategoryDto();
        categoryDto.setId(ID);

        when(categoryRepository.findById(ID)).thenReturn(Optional.empty());

        assertThatExceptionOfType(CategoryNotFoundException.class)
                .isThrownBy(() -> categoryService.update(categoryDto))
                .withMessage("Category was not found!");
        verify(categoryRepository, times(1)).findById(ID);
        verify(categoryRepository, times(0)).save(any());
    }

    @Test
    void givenCategoryDtoWithDuplicateTranslationName_whenUpdate_thenThrowTranslationAlreadyExistsException() {
        CategoryDto categoryDto = createCategoryDto();
        categoryDto.setId(ID);
        Category category = createCategory();

        when(categoryRepository.findById(ID)).thenReturn(Optional.of(category));
        when(categoryRepository.existsTranslation(NAME_EN)).thenReturn(false);
        when(categoryRepository.existsTranslation(NAME_UA)).thenReturn(true);

        assertThatExceptionOfType(TranslationAlreadyExistsException.class)
                .isThrownBy(() -> categoryService.update(categoryDto))
                .withMessage("Translation with this name already exists!");
        verify(categoryRepository, times(1)).existsTranslation(NAME_EN);
        verify(categoryRepository, times(1)).existsTranslation(NAME_UA);
        verify(categoryRepository, times(0)).save(any());
    }

    @Test
    void givenValidCategoryDto_whenUpdate_thenReturnUpdatedCategoryDto() {
        CategoryDto categoryDto = createCategoryDto();
        categoryDto.setId(ID);
        Category category = createCategory();
        category.getTranslations().removeIf(t -> t.getLang().equals("en"));

        when(categoryRepository.findById(ID)).thenReturn(Optional.of(category));
        when(categoryRepository.existsTranslation(NAME_EN)).thenReturn(false);
        when(categoryRepository.existsTranslation(NAME_UA)).thenReturn(false);
        when(categoryRepository.save(createCategory())).thenReturn(createCategory());

        CategoryDto updatedCategoryDto = categoryService.update(categoryDto);

        assertThat(updatedCategoryDto, is(categoryDto));
        verify(categoryRepository, times(1)).save(createCategory());
    }

    @Test
    void givenInvalidCategoryId_whenDelete_thenThrowCategoryNotFoundException() {
        when(categoryRepository.existsById(ID)).thenReturn(false);

        assertThatExceptionOfType(CategoryNotFoundException.class)
                .isThrownBy(() -> categoryService.delete(ID))
                .withMessage("Category was not found!");

        verify(categoryRepository, times(1)).existsById(ID);
        verify(categoryRepository, times(0)).deleteById(ID);
    }

    @Test
    void givenValidActivityId_whenDelete_thenRepositoryMethodCall() {
        doNothing().when(categoryRepository).deleteById(ID);
        when(categoryRepository.existsById(ID)).thenReturn(true);

        categoryService.delete(ID);

        verify(categoryRepository, times(1)).deleteById(ID);
    }

}
