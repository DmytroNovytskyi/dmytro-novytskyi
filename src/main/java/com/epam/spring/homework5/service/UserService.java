package com.epam.spring.homework5.service;

import com.epam.spring.homework5.controller.dto.UserDto;
import org.springframework.data.domain.Page;

public interface UserService {

    Page<UserDto> getAll(int page, int size, String sortBy, String order);

    UserDto create(UserDto user);

    UserDto update(UserDto user);

    void delete(int userId);

}
