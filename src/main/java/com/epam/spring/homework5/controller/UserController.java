package com.epam.spring.homework5.controller;

import com.epam.spring.homework5.controller.api.UserApi;
import com.epam.spring.homework5.controller.dto.UserDto;
import com.epam.spring.homework5.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class UserController implements UserApi {

    private final UserService userService;

    @Override
    public Page<UserDto> getSortedPagedUsers(int page, int size, String sortBy, String order) {
        log.info("accepted request to get users");
        return userService.getAll(page, size, sortBy, order);
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
        userService.delete(userId);
        return ResponseEntity.noContent().build();
    }

}
