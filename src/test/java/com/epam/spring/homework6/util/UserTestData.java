package com.epam.spring.homework6.util;

import com.epam.spring.homework6.controller.dto.UserDto;
import com.epam.spring.homework6.service.model.User;
import com.epam.spring.homework6.service.model.enums.Role;
import com.epam.spring.homework6.service.model.enums.UserStatus;

import java.util.ArrayList;
import java.util.List;

public class UserTestData {

    public static final Integer ID = 1;
    public static final String USERNAME = "username";
    public static final String EMAIL = "username@gmail.com";
    public static final String PASSWORD = "password1!";

    public static List<UserDto> createUserDtoList() {
        List<UserDto> userDtoList = new ArrayList<>();
        UserDto firstUserDto = new UserDto();
        firstUserDto.setId(1);
        firstUserDto.setUsername("username1");
        firstUserDto.setEmail("username1@gmail.com");
        firstUserDto.setStatus(UserStatus.ACTIVE.name());
        firstUserDto.setRole(Role.ADMIN.name());
        UserDto secondUserDto = new UserDto();
        secondUserDto.setId(2);
        secondUserDto.setUsername("username2");
        secondUserDto.setEmail("username2@gmail.com");
        secondUserDto.setStatus(UserStatus.ACTIVE.name());
        secondUserDto.setRole(Role.WORKER.name());
        userDtoList.add(firstUserDto);
        userDtoList.add(secondUserDto);
        return userDtoList;
    }

    public static List<User> createUserList() {
        List<User> users = new ArrayList<>();
        users.add(User.builder()
                .id(1)
                .username("username1")
                .email("username1@gmail.com")
                .status(UserStatus.ACTIVE)
                .role(Role.ADMIN)
                .build());
        users.add(User.builder()
                .id(2)
                .username("username2")
                .email("username2@gmail.com")
                .status(UserStatus.ACTIVE)
                .role(Role.WORKER)
                .build());
        return users;
    }

    public static User createUser() {
        return User.builder()
                .id(ID)
                .username(USERNAME)
                .email(EMAIL)
                .role(Role.ADMIN)
                .status(UserStatus.ACTIVE)
                .password(PASSWORD)
                .build();
    }

    public static UserDto createUserDto() {
        UserDto userDto = new UserDto();
        userDto.setUsername(USERNAME);
        userDto.setEmail(EMAIL);
        userDto.setRole(Role.ADMIN.name());
        userDto.setStatus(UserStatus.ACTIVE.name());
        return userDto;
    }

}
