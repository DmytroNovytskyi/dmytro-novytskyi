package com.epam.spring.homework6.controller;

import com.epam.spring.homework6.controller.dto.UserDto;
import com.epam.spring.homework6.service.UserService;
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

import static com.epam.spring.homework6.util.CommonTestData.*;
import static com.epam.spring.homework6.util.UserTestData.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    void givenPageableData_whenGetSortedPagedActivities_thenReturnPageOfActivities() throws Exception {
        List<UserDto> userDtoList = createUserDtoList();
        Page<UserDto> userDtoPage = createPage(userDtoList);
        when(userService.getAll(PAGE, SIZE, SORT_BY, ORDER)).thenReturn(userDtoPage);

        mockMvc.perform(get("/api/v1/user")
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
                .andExpect(jsonPath("$.content[0].id").value(userDtoList.get(0).getId()))
                .andExpect(jsonPath("$.content[1].id").value(userDtoList.get(1).getId()));
        verify(userService, times(1)).getAll(PAGE, SIZE, SORT_BY, ORDER);
    }

    @Test
    void givenValidUserDto_whenCreateUser_thenReturnCreatedUserDto() throws Exception {
        UserDto userDtoBeforeCreate = createUserDto();
        userDtoBeforeCreate.setPassword(PASSWORD);
        userDtoBeforeCreate.setRepeatPassword(PASSWORD);
        UserDto userDtoAfterCreate = createUserDto();
        userDtoAfterCreate.setId(ID);
        userDtoAfterCreate.setPassword(PASSWORD);
        userDtoAfterCreate.setRepeatPassword(PASSWORD);
        when(userService.create(userDtoBeforeCreate)).thenReturn(userDtoAfterCreate);

        mockMvc.perform(post("/api/v1/user")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(userDtoBeforeCreate)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(ID));
        verify(userService, times(1)).create(userDtoBeforeCreate);
    }

    @Test
    void givenInvalidUserDto_whenCreateUser_thenReturnErrorListJson() throws Exception {
        UserDto userDto = createUserDto();
        userDto.setId(ID);
        userDto.setUsername(null);
        userDto.setRole(null);
        userDto.setEmail(null);
        userDto.setStatus(null);
        when(userService.create(userDto)).thenReturn(userDto);

        mockMvc.perform(post("/api/v1/user")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(userDto)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].errorType").value(ErrorType.VALIDATION_ERROR_TYPE.name()))
                .andExpect(jsonPath("$[1].errorType").value(ErrorType.VALIDATION_ERROR_TYPE.name()))
                .andExpect(jsonPath("$[2].errorType").value(ErrorType.VALIDATION_ERROR_TYPE.name()))
                .andExpect(jsonPath("$[3].errorType").value(ErrorType.VALIDATION_ERROR_TYPE.name()))
                .andExpect(jsonPath("$[4].errorType").value(ErrorType.VALIDATION_ERROR_TYPE.name()))
                .andExpect(jsonPath("$[5].errorType").value(ErrorType.VALIDATION_ERROR_TYPE.name()))
                .andExpect(jsonPath("$[6].errorType").value(ErrorType.VALIDATION_ERROR_TYPE.name()));
        verify(userService, times(0)).create(userDto);
    }

    @Test
    void givenValidUserDto_whenUpdateUser_thenReturnUpdatedUserDto() throws Exception {
        UserDto userDto = createUserDto();
        userDto.setId(ID);
        userDto.setUsername(null);
        userDto.setRole(null);
        when(userService.update(userDto)).thenReturn(userDto);

        mockMvc.perform(put("/api/v1/user")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(userDto)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(ID));
        verify(userService, times(1)).update(userDto);
    }

    @Test
    void givenInvalidUserDto_whenUpdateUser_thenReturnErrorListJson() throws Exception {
        UserDto userDto = createUserDto();
        when(userService.update(userDto)).thenReturn(userDto);

        mockMvc.perform(put("/api/v1/user")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(userDto)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].errorType").value(ErrorType.VALIDATION_ERROR_TYPE.name()))
                .andExpect(jsonPath("$[1].errorType").value(ErrorType.VALIDATION_ERROR_TYPE.name()))
                .andExpect(jsonPath("$[2].errorType").value(ErrorType.VALIDATION_ERROR_TYPE.name()));
        verify(userService, times(0)).update(userDto);
    }

    @Test
    void givenUserId_whenDeleteUser_thenDeleteAndReturnNothing() throws Exception {
        doNothing().when(userService).delete(ID);

        mockMvc.perform(delete("/api/v1/user/" + ID))
                .andDo(print())
                .andExpect(status().isNoContent());
        verify(userService, times(1)).delete(ID);
    }

}
