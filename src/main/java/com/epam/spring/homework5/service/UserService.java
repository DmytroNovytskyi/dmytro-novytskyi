package com.epam.spring.homework5.service;

import com.epam.spring.homework5.controller.dto.UserDto;

import java.util.List;

public interface UserService {

    List<UserDto> getAll();

    UserDto create(UserDto user);

    UserDto update(UserDto user);

    void delete(int userId);

}
