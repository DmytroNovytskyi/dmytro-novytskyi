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
