package com.epam.spring.homework6.controller;

import com.epam.spring.homework6.controller.dto.UserHasActivityDto;
import com.epam.spring.homework6.service.UserHasActivityService;
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
import static com.epam.spring.homework6.util.UserHasActivityTestData.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserHasActivityController.class)
@AutoConfigureMockMvc
public class UserHasActivityControllerTest {

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserHasActivityService userHasActivityService;

    @Test
    void givenPageableData_whenGetSortedPagedUserHasActivities_thenReturnPageOfUserHasActivities() throws Exception {
        List<UserHasActivityDto> userHasActivityDtoList = createUserHasActivityDtoList();
        Page<UserHasActivityDto> activityDtoPage = createPage(userHasActivityDtoList);
        when(userHasActivityService.getAll(PAGE, SIZE, SORT_BY, ORDER)).thenReturn(activityDtoPage);

        mockMvc.perform(get("/api/v1/user-has-activity").queryParam("page", String.valueOf(PAGE)).queryParam("size", String.valueOf(SIZE)).queryParam("sortBy", SORT_BY).queryParam("order", ORDER)).andDo(print()).andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON)).andExpect(jsonPath("$.pageable.pageNumber").value(PAGE)).andExpect(jsonPath("$.pageable.pageSize").value(SIZE)).andExpect(jsonPath("$.pageable.sort.sorted").value(true)).andExpect(jsonPath("$.content[0].id").value(userHasActivityDtoList.get(0).getId())).andExpect(jsonPath("$.content[1].id").value(userHasActivityDtoList.get(1).getId()));
        verify(userHasActivityService, times(1)).getAll(PAGE, SIZE, SORT_BY, ORDER);
    }

    @Test
    void givenValidUserHasActivityDto_whenRequestActivity_thenReturnCreatedUserHasActivityDto() throws Exception {
        UserHasActivityDto userHasActivityDtoBeforeCreate = createUserHasActivityDto();
        userHasActivityDtoBeforeCreate.setStatus(null);
        UserHasActivityDto userHasActivityDtoAfterCreate = createUserHasActivityDto();
        userHasActivityDtoAfterCreate.setId(ID);
        when(userHasActivityService.request(userHasActivityDtoBeforeCreate)).thenReturn(userHasActivityDtoAfterCreate);

        mockMvc.perform(post("/api/v1/user-has-activity").contentType("application/json").content(objectMapper.writeValueAsString(userHasActivityDtoBeforeCreate))).andDo(print()).andExpect(status().isCreated()).andExpect(content().contentType(MediaType.APPLICATION_JSON)).andExpect(jsonPath("$.id").value(ID));
        verify(userHasActivityService, times(1)).request(userHasActivityDtoBeforeCreate);
    }

    @Test
    void givenInvalidUserHasActivityDto_whenRequestActivity_thenReturnErrorListJson() throws Exception {
        UserHasActivityDto userHasActivityDto = createUserHasActivityDto();
        userHasActivityDto.setId(ID);
        userHasActivityDto.setUser(null);
        userHasActivityDto.setActivity(null);
        userHasActivityDto.setCreationDate(new Timestamp(LocalDateTime.now().getNano() / 1000));
        userHasActivityDto.setStartTime(new Timestamp(LocalDateTime.now().getNano() / 1000));
        userHasActivityDto.setEndTime(new Timestamp(LocalDateTime.now().getNano() / 1000));
        userHasActivityDto.setTimeSpent("00:00:00");
        when(userHasActivityService.request(userHasActivityDto)).thenReturn(userHasActivityDto);

        mockMvc.perform(post("/api/v1/user-has-activity").contentType("application/json").content(objectMapper.writeValueAsString(userHasActivityDto))).andDo(print()).andExpect(status().isBadRequest()).andExpect(content().contentType(MediaType.APPLICATION_JSON)).andExpect(jsonPath("$[0].errorType").value(ErrorType.VALIDATION_ERROR_TYPE.name())).andExpect(jsonPath("$[1].errorType").value(ErrorType.VALIDATION_ERROR_TYPE.name())).andExpect(jsonPath("$[2].errorType").value(ErrorType.VALIDATION_ERROR_TYPE.name())).andExpect(jsonPath("$[3].errorType").value(ErrorType.VALIDATION_ERROR_TYPE.name())).andExpect(jsonPath("$[4].errorType").value(ErrorType.VALIDATION_ERROR_TYPE.name())).andExpect(jsonPath("$[5].errorType").value(ErrorType.VALIDATION_ERROR_TYPE.name())).andExpect(jsonPath("$[6].errorType").value(ErrorType.VALIDATION_ERROR_TYPE.name())).andExpect(jsonPath("$[7].errorType").value(ErrorType.VALIDATION_ERROR_TYPE.name()));
        verify(userHasActivityService, times(0)).request(userHasActivityDto);
    }

    @Test
    void givenValidUserHasActivityDto_whenUpdateUserHasActivity_thenReturnUpdatedUserHasActivityDto() throws Exception {
        UserHasActivityDto userHasActivityDtoBeforeUpdate = createUserHasActivityDto();
        userHasActivityDtoBeforeUpdate.setId(ID);
        userHasActivityDtoBeforeUpdate.setUser(null);
        userHasActivityDtoBeforeUpdate.setActivity(null);
        UserHasActivityDto userHasActivityDtoAfterUpdate = createUserHasActivityDto();
        userHasActivityDtoAfterUpdate.setId(ID);
        when(userHasActivityService.update(userHasActivityDtoBeforeUpdate)).thenReturn(userHasActivityDtoAfterUpdate);

        mockMvc.perform(put("/api/v1/user-has-activity").contentType("application/json").content(objectMapper.writeValueAsString(userHasActivityDtoBeforeUpdate))).andDo(print()).andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON)).andExpect(jsonPath("$.id").value(ID));
        verify(userHasActivityService, times(1)).update(userHasActivityDtoBeforeUpdate);
    }

    @Test
    void givenInvalidUserHasActivityDto_whenUpdateUserHasActivity_thenReturnErrorListJson() throws Exception {
        UserHasActivityDto userHasActivityDto = createUserHasActivityDto();
        userHasActivityDto.setStatus(null);
        userHasActivityDto.setCreationDate(new Timestamp(LocalDateTime.now().getNano() / 1000));
        userHasActivityDto.setStartTime(new Timestamp(LocalDateTime.now().getNano() / 1000));
        userHasActivityDto.setEndTime(new Timestamp(LocalDateTime.now().getNano() / 1000));
        userHasActivityDto.setTimeSpent("00:00:00");
        when(userHasActivityService.update(userHasActivityDto)).thenReturn(userHasActivityDto);

        mockMvc.perform(put("/api/v1/user-has-activity").contentType("application/json").content(objectMapper.writeValueAsString(userHasActivityDto))).andDo(print()).andExpect(status().isBadRequest()).andExpect(content().contentType(MediaType.APPLICATION_JSON)).andExpect(jsonPath("$[0].errorType").value(ErrorType.VALIDATION_ERROR_TYPE.name())).andExpect(jsonPath("$[1].errorType").value(ErrorType.VALIDATION_ERROR_TYPE.name())).andExpect(jsonPath("$[2].errorType").value(ErrorType.VALIDATION_ERROR_TYPE.name())).andExpect(jsonPath("$[3].errorType").value(ErrorType.VALIDATION_ERROR_TYPE.name())).andExpect(jsonPath("$[4].errorType").value(ErrorType.VALIDATION_ERROR_TYPE.name())).andExpect(jsonPath("$[5].errorType").value(ErrorType.VALIDATION_ERROR_TYPE.name())).andExpect(jsonPath("$[6].errorType").value(ErrorType.VALIDATION_ERROR_TYPE.name())).andExpect(jsonPath("$[7].errorType").value(ErrorType.VALIDATION_ERROR_TYPE.name()));
        verify(userHasActivityService, times(0)).update(userHasActivityDto);
    }

    @Test
    void givenUserHasActivityId_whenDeleteUserHasActivity_thenDeleteAndReturnNothing() throws Exception {
        doNothing().when(userHasActivityService).delete(ID);

        mockMvc.perform(delete("/api/v1/user-has-activity/" + ID)).andDo(print()).andExpect(status().isNoContent());
        verify(userHasActivityService, times(1)).delete(ID);
    }

}
