package com.epam.spring.homework4.controller;

import com.epam.spring.homework4.controller.api.UserApi;
import com.epam.spring.homework4.controller.dto.UserDto;
import com.epam.spring.homework4.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class UserController implements UserApi {

    private final UserService userService;

    @Override
    public List<UserDto> getAllUsers() {
        log.info("accepted request to get all users");
        return userService.getAll();
    }

    @Override
    public UserDto createUser(UserDto user) {
        log.info("accepted request to create user with name:{}", user.getUsername());
        return userService.create(user);
    }

    @Override
    public UserDto updateUser(UserDto user) {
        log.info("accepted request to update user with name:{}", user.getUsername());
        return userService.update(user);
    }

    @Override
    public ResponseEntity<Void> deleteUser(int userId) {
        log.info("accepted request to delete user with id:{}", userId);
        userService.deleteById(userId);
        return ResponseEntity.noContent().build();
    }

}
