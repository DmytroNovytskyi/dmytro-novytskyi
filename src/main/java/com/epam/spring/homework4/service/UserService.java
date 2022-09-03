package com.epam.spring.homework4.service;

import com.epam.spring.homework4.controller.dto.UserDto;

import java.util.List;

public interface UserService {

    List<UserDto> getAll();

    UserDto create(UserDto user);

    UserDto update(UserDto user);

    void deleteById(int userId);

}
