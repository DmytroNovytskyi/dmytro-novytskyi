package com.epam.spring.homework3.controller;

import com.epam.spring.homework3.controller.dto.UserDto;
import com.epam.spring.homework3.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public List<UserDto> getAllUsers() {
        log.info("accepted request to get all users");
        return userService.getAll();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public UserDto createUser(@RequestBody UserDto user) {
        log.info("accepted request to create user with name:{}", user.getUsername());
        return userService.create(user);
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping
    public UserDto updateUser(@RequestBody UserDto user) {
        log.info("accepted request to update user with name:{}", user.getUsername());
        return userService.update(user);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable int userId) {
        log.info("accepted request to delete user with id:{}", userId);
        userService.deleteById(userId);
        return ResponseEntity.noContent().build();
    }

}
