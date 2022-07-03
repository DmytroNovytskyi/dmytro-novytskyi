package com.epam.spring.timekeeper.service;

import com.epam.spring.timekeeper.controller.dto.UserDto;

import java.util.List;

public interface UserService {

    List<UserDto> getAll();

    UserDto create(UserDto user);

    UserDto update(UserDto user);

    void delete(int userId);

}
