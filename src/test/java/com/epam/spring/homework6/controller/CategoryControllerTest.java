package com.epam.spring.homework6.controller;

import com.epam.spring.homework6.controller.dto.CategoryDto;
import com.epam.spring.homework6.service.CategoryService;
import com.epam.spring.homework6.service.model.enums.ErrorType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static com.epam.spring.homework6.util.CategoryTestData.*;
import static com.epam.spring.homework6.util.CommonTestData.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CategoryController.class)
@AutoConfigureMockMvc
public class CategoryControllerTest {

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CategoryService categoryService;

    @Test
    void givenPageableData_whenGetSortedPagedCategories_thenReturnPageOfCategories() throws Exception {
        List<CategoryDto> categoryDtoList = createCategoryDtoList();
        Page<CategoryDto> categoryDtoPage = createPage(categoryDtoList);
        when(categoryService.getAll(PAGE, SIZE, SORT_BY, ORDER)).thenReturn(categoryDtoPage);

        mockMvc.perform(get("/api/v1/category")
                        .queryParam("page", String.valueOf(PAGE))
                        .queryParam("size", String.valueOf(SIZE))
                        .queryParam("sortBy", SORT_BY)
                        .queryParam("order", ORDER))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.pageable.pageNumber").value(PAGE))
                .andExpect(jsonPath("$.pageable.pageSize").value(SIZE))
                .andExpect(jsonPath("$.pageable.sort.sorted").value(true))
                .andExpect(jsonPath("$.content[0].id").value(categoryDtoList.get(0).getId()))
                .andExpect(jsonPath("$.content[1].id").value(categoryDtoList.get(1).getId()));
        verify(categoryService, times(1)).getAll(PAGE, SIZE, SORT_BY, ORDER);
    }

    @Test
    void givenValidCategoryDto_whenCreateCategory_thenReturnCreatedCategoryDto() throws Exception {
        CategoryDto categoryDtoBeforeCreate = createCategoryDto();
        categoryDtoBeforeCreate.setId(null);
        CategoryDto categoryDtoAfterCreate = createCategoryDto();
        when(categoryService.create(categoryDtoBeforeCreate)).thenReturn(categoryDtoAfterCreate);

        mockMvc.perform(post("/api/v1/category")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(categoryDtoBeforeCreate)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(ID));
        verify(categoryService, times(1)).create(categoryDtoBeforeCreate);
    }

    @Test
    void givenInvalidCategoryDto_whenCreateCategory_thenReturnErrorListJson() throws Exception {
        CategoryDto categoryDto = createCategoryDto();
        categoryDto.setId(ID);
        categoryDto.setStatus(null);
        categoryDto.setTranslations(null);
        when(categoryService.create(categoryDto)).thenReturn(categoryDto);

        mockMvc.perform(post("/api/v1/category")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(categoryDto)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].errorType").value(ErrorType.VALIDATION_ERROR_TYPE.name()))
                .andExpect(jsonPath("$[1].errorType").value(ErrorType.VALIDATION_ERROR_TYPE.name()))
                .andExpect(jsonPath("$[2].errorType").value(ErrorType.VALIDATION_ERROR_TYPE.name()));
        verify(categoryService, times(0)).create(categoryDto);
    }

    @Test
    void givenValidCategoryDto_whenUpdateCategory_thenReturnUpdatedCategoryDto() throws Exception {
        CategoryDto categoryDto = createCategoryDto();
        when(categoryService.update(categoryDto)).thenReturn(categoryDto);

        mockMvc.perform(put("/api/v1/category")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(categoryDto)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(ID));
        verify(categoryService, times(1)).update(categoryDto);
    }

    @Test
    void givenInvalidCategoryDto_whenUpdateCategory_thenReturnErrorListJson() throws Exception {
        CategoryDto categoryDto = createCategoryDto();
        categoryDto.setId(null);
        when(categoryService.update(categoryDto)).thenReturn(categoryDto);

        mockMvc.perform(put("/api/v1/category")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(categoryDto)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].errorType").value(ErrorType.VALIDATION_ERROR_TYPE.name()));
        verify(categoryService, times(0)).update(categoryDto);
    }

    @Test
    void givenCategoryId_whenDeleteCategory_thenDeleteAndReturnNothing() throws Exception {
        doNothing().when(categoryService).delete(ID);

        mockMvc.perform(delete("/api/v1/category/" + ID))
                .andDo(print())
                .andExpect(status().isNoContent());
        verify(categoryService, times(1)).delete(ID);
    }

}
