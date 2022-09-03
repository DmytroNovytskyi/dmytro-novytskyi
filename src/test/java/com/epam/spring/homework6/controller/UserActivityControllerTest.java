package com.epam.spring.homework6.controller;

import com.epam.spring.homework6.controller.dto.UserActivityDto;
import com.epam.spring.homework6.service.UserActivityService;
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

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import static com.epam.spring.homework6.util.CommonTestData.*;
import static com.epam.spring.homework6.util.UserActivityTestData.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserActivityController.class)
@AutoConfigureMockMvc
public class UserActivityControllerTest {

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserActivityService userActivityService;

    @Test
    void givenPageableData_whenGetSortedPagedUserActivities_thenReturnPageOfUserActivities() throws Exception {
        List<UserActivityDto> userActivityDtoList = createUserActivityDtoList();
        Page<UserActivityDto> activityDtoPage = createPage(userActivityDtoList);
        when(userActivityService.getAll(PAGE, SIZE, SORT_BY, ORDER)).thenReturn(activityDtoPage);

        mockMvc.perform(get("/api/v1/user-activity").queryParam("page", String.valueOf(PAGE)).queryParam("size", String.valueOf(SIZE)).queryParam("sortBy", SORT_BY).queryParam("order", ORDER)).andDo(print()).andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON)).andExpect(jsonPath("$.pageable.pageNumber").value(PAGE)).andExpect(jsonPath("$.pageable.pageSize").value(SIZE)).andExpect(jsonPath("$.pageable.sort.sorted").value(true)).andExpect(jsonPath("$.content[0].id").value(userActivityDtoList.get(0).getId())).andExpect(jsonPath("$.content[1].id").value(userActivityDtoList.get(1).getId()));
        verify(userActivityService, times(1)).getAll(PAGE, SIZE, SORT_BY, ORDER);
    }

    @Test
    void givenValidUserActivityDto_whenRequestActivity_thenReturnCreatedUserActivityDto() throws Exception {
        UserActivityDto userActivityDtoBeforeCreate = createUserActivityDto();
        userActivityDtoBeforeCreate.setStatus(null);
        UserActivityDto userActivityDtoAfterCreate = createUserActivityDto();
        userActivityDtoAfterCreate.setId(ID);
        when(userActivityService.request(userActivityDtoBeforeCreate)).thenReturn(userActivityDtoAfterCreate);

        mockMvc.perform(post("/api/v1/user-activity").contentType("application/json").content(objectMapper.writeValueAsString(userActivityDtoBeforeCreate))).andDo(print()).andExpect(status().isCreated()).andExpect(content().contentType(MediaType.APPLICATION_JSON)).andExpect(jsonPath("$.id").value(ID));
        verify(userActivityService, times(1)).request(userActivityDtoBeforeCreate);
    }

    @Test
    void givenInvalidUserActivityDto_whenRequestActivity_thenReturnErrorListJson() throws Exception {
        UserActivityDto userActivityDto = createUserActivityDto();
        userActivityDto.setId(ID);
        userActivityDto.setUser(null);
        userActivityDto.setActivity(null);
        userActivityDto.setCreationDate(new Timestamp(LocalDateTime.now().getNano() / 1000));
        userActivityDto.setStartTime(new Timestamp(LocalDateTime.now().getNano() / 1000));
        userActivityDto.setEndTime(new Timestamp(LocalDateTime.now().getNano() / 1000));
        userActivityDto.setTimeSpent("00:00:00");
        when(userActivityService.request(userActivityDto)).thenReturn(userActivityDto);

        mockMvc.perform(post("/api/v1/user-activity").contentType("application/json").content(objectMapper.writeValueAsString(userActivityDto))).andDo(print()).andExpect(status().isBadRequest()).andExpect(content().contentType(MediaType.APPLICATION_JSON)).andExpect(jsonPath("$[0].errorType").value(ErrorType.VALIDATION_ERROR_TYPE.name())).andExpect(jsonPath("$[1].errorType").value(ErrorType.VALIDATION_ERROR_TYPE.name())).andExpect(jsonPath("$[2].errorType").value(ErrorType.VALIDATION_ERROR_TYPE.name())).andExpect(jsonPath("$[3].errorType").value(ErrorType.VALIDATION_ERROR_TYPE.name())).andExpect(jsonPath("$[4].errorType").value(ErrorType.VALIDATION_ERROR_TYPE.name())).andExpect(jsonPath("$[5].errorType").value(ErrorType.VALIDATION_ERROR_TYPE.name())).andExpect(jsonPath("$[6].errorType").value(ErrorType.VALIDATION_ERROR_TYPE.name())).andExpect(jsonPath("$[7].errorType").value(ErrorType.VALIDATION_ERROR_TYPE.name()));
        verify(userActivityService, times(0)).request(userActivityDto);
    }

    @Test
    void givenValidUserActivityDto_whenUpdateUserActivity_thenReturnUpdatedUserActivityDto() throws Exception {
        UserActivityDto userActivityDtoBeforeUpdate = createUserActivityDto();
        userActivityDtoBeforeUpdate.setId(ID);
        userActivityDtoBeforeUpdate.setUser(null);
        userActivityDtoBeforeUpdate.setActivity(null);
        UserActivityDto userActivityDtoAfterUpdate = createUserActivityDto();
        userActivityDtoAfterUpdate.setId(ID);
        when(userActivityService.update(userActivityDtoBeforeUpdate)).thenReturn(userActivityDtoAfterUpdate);

        mockMvc.perform(put("/api/v1/user-activity").contentType("application/json").content(objectMapper.writeValueAsString(userActivityDtoBeforeUpdate))).andDo(print()).andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON)).andExpect(jsonPath("$.id").value(ID));
        verify(userActivityService, times(1)).update(userActivityDtoBeforeUpdate);
    }

    @Test
    void givenInvalidUserActivityDto_whenUpdateUserActivity_thenReturnErrorListJson() throws Exception {
        UserActivityDto userActivityDto = createUserActivityDto();
        userActivityDto.setStatus(null);
        userActivityDto.setCreationDate(new Timestamp(LocalDateTime.now().getNano() / 1000));
        userActivityDto.setStartTime(new Timestamp(LocalDateTime.now().getNano() / 1000));
        userActivityDto.setEndTime(new Timestamp(LocalDateTime.now().getNano() / 1000));
        userActivityDto.setTimeSpent("00:00:00");
        when(userActivityService.update(userActivityDto)).thenReturn(userActivityDto);

        mockMvc.perform(put("/api/v1/user-activity").contentType("application/json").content(objectMapper.writeValueAsString(userActivityDto))).andDo(print()).andExpect(status().isBadRequest()).andExpect(content().contentType(MediaType.APPLICATION_JSON)).andExpect(jsonPath("$[0].errorType").value(ErrorType.VALIDATION_ERROR_TYPE.name())).andExpect(jsonPath("$[1].errorType").value(ErrorType.VALIDATION_ERROR_TYPE.name())).andExpect(jsonPath("$[2].errorType").value(ErrorType.VALIDATION_ERROR_TYPE.name())).andExpect(jsonPath("$[3].errorType").value(ErrorType.VALIDATION_ERROR_TYPE.name())).andExpect(jsonPath("$[4].errorType").value(ErrorType.VALIDATION_ERROR_TYPE.name())).andExpect(jsonPath("$[5].errorType").value(ErrorType.VALIDATION_ERROR_TYPE.name())).andExpect(jsonPath("$[6].errorType").value(ErrorType.VALIDATION_ERROR_TYPE.name())).andExpect(jsonPath("$[7].errorType").value(ErrorType.VALIDATION_ERROR_TYPE.name()));
        verify(userActivityService, times(0)).update(userActivityDto);
    }

    @Test
    void givenUserActivityId_whenDeleteUserActivity_thenDeleteAndReturnNothing() throws Exception {
        doNothing().when(userActivityService).deleteById(ID);

        mockMvc.perform(delete("/api/v1/user-activity/" + ID)).andDo(print()).andExpect(status().isNoContent());
        verify(userActivityService, times(1)).deleteById(ID);
    }

}
