package com.epam.spring.homework6.controller;

import com.epam.spring.homework6.controller.dto.ActivityDto;
import com.epam.spring.homework6.service.ActivityService;
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

import static com.epam.spring.homework6.util.ActivityTestData.*;
import static com.epam.spring.homework6.util.CommonTestData.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ActivityController.class)
@AutoConfigureMockMvc
public class ActivityControllerTest {

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ActivityService activityService;

    @Test
    void givenPageableData_whenGetSortedPagedActivities_thenReturnPageOfActivities() throws Exception {
        List<ActivityDto> activityDtoList = createActivityDtoList();
        Page<ActivityDto> activityDtoPage = createPage(activityDtoList);
        when(activityService.getAll(PAGE, SIZE, SORT_BY, ORDER)).thenReturn(activityDtoPage);

        mockMvc.perform(get("/api/v1/activity")
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
                .andExpect(jsonPath("$.content[0].id").value(activityDtoList.get(0).getId()))
                .andExpect(jsonPath("$.content[1].id").value(activityDtoList.get(1).getId()));
        verify(activityService, times(1)).getAll(PAGE, SIZE, SORT_BY, ORDER);
    }

    @Test
    void givenValidActivityDto_whenCreateActivity_thenReturnCreatedActivityDto() throws Exception {
        ActivityDto activityDtoBeforeCreate = createActivityDto();
        activityDtoBeforeCreate.setUserCount(null);
        ActivityDto activityDtoAfterCreate = createActivityDto();
        activityDtoAfterCreate.setId(ID);
        when(activityService.create(activityDtoBeforeCreate)).thenReturn(activityDtoAfterCreate);

        mockMvc.perform(post("/api/v1/activity")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(activityDtoBeforeCreate)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(ID));
        verify(activityService, times(1)).create(activityDtoBeforeCreate);
    }

    @Test
    void givenInvalidActivityDto_whenCreateActivity_thenReturnErrorListJson() throws Exception {
        ActivityDto activityDto = createActivityDto();
        activityDto.setId(ID);
        activityDto.setCategory(null);
        activityDto.setStatus(null);
        activityDto.setName(null);
        activityDto.setDescription("a".repeat(256));
        when(activityService.create(activityDto)).thenReturn(activityDto);

        mockMvc.perform(post("/api/v1/activity")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(activityDto)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].errorType").value(ErrorType.VALIDATION_ERROR_TYPE.name()))
                .andExpect(jsonPath("$[1].errorType").value(ErrorType.VALIDATION_ERROR_TYPE.name()))
                .andExpect(jsonPath("$[2].errorType").value(ErrorType.VALIDATION_ERROR_TYPE.name()))
                .andExpect(jsonPath("$[3].errorType").value(ErrorType.VALIDATION_ERROR_TYPE.name()))
                .andExpect(jsonPath("$[4].errorType").value(ErrorType.VALIDATION_ERROR_TYPE.name()))
                .andExpect(jsonPath("$[5].errorType").value(ErrorType.VALIDATION_ERROR_TYPE.name()));
        verify(activityService, times(0)).create(activityDto);
    }

    @Test
    void givenValidActivityDto_whenUpdateActivity_thenReturnUpdatedActivityDto() throws Exception {
        ActivityDto activityDtoBeforeUpdate = createActivityDto();
        activityDtoBeforeUpdate.setId(ID);
        activityDtoBeforeUpdate.setUserCount(null);
        ActivityDto activityDtoAfterUpdate = createActivityDto();
        activityDtoAfterUpdate.setId(ID);
        when(activityService.update(activityDtoBeforeUpdate)).thenReturn(activityDtoAfterUpdate);

        mockMvc.perform(put("/api/v1/activity")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(activityDtoBeforeUpdate)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(ID));
        verify(activityService, times(1)).update(activityDtoBeforeUpdate);
    }

    @Test
    void givenInvalidActivityDto_whenUpdateActivity_thenReturnErrorListJson() throws Exception {
        ActivityDto activityDto = createActivityDto();
        activityDto.setCategory(null);
        activityDto.setName(null);
        activityDto.setDescription("a".repeat(256));
        when(activityService.update(activityDto)).thenReturn(activityDto);

        mockMvc.perform(put("/api/v1/activity")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(activityDto)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].errorType").value(ErrorType.VALIDATION_ERROR_TYPE.name()))
                .andExpect(jsonPath("$[1].errorType").value(ErrorType.VALIDATION_ERROR_TYPE.name()))
                .andExpect(jsonPath("$[2].errorType").value(ErrorType.VALIDATION_ERROR_TYPE.name()));
        verify(activityService, times(0)).update(activityDto);
    }

    @Test
    void givenActivityId_whenDeleteActivity_thenDeleteAndReturnNothing() throws Exception {
        doNothing().when(activityService).deleteById(ID);

        mockMvc.perform(delete("/api/v1/activity/" + ID))
                .andDo(print())
                .andExpect(status().isNoContent());
        verify(activityService, times(1)).deleteById(ID);
    }

}
