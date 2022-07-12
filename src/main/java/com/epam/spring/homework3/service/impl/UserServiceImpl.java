package com.epam.spring.homework3.service.impl;

import com.epam.spring.homework3.controller.dto.UserDto;
import com.epam.spring.homework3.service.UserService;
import com.epam.spring.homework3.service.exception.NotFoundException;
import com.epam.spring.homework3.service.mapper.UserMapper;
import com.epam.spring.homework3.service.model.User;
import com.epam.spring.homework3.service.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public List<UserDto> getAll() {
        log.info("reading all users");
        return userRepository.findAll().stream()
                .map(UserMapper.INSTANCE::mapUserDto)
                .sorted(Comparator.comparing(UserDto::getUsername))
                .toList();
    }

    @Override
    public UserDto create(UserDto user) {
        log.info("creating user with name:{}", user.getUsername());
        User created = userRepository.create(UserMapper.INSTANCE.mapUser(user));
        return UserMapper.INSTANCE.mapUserDto(created);
    }

    @Override
    public UserDto update(UserDto user) {
        log.info("updating user with id:{}", user.getId());
        if (userRepository.findById(user.getId()) == null) {
            throw new NotFoundException("No user with id=" + user.getId() + " was found to update.");
        }
        User updated = userRepository.update(UserMapper.INSTANCE.mapUser(user));
        return UserMapper.INSTANCE.mapUserDto(updated);
    }

    @Override
    public void delete(int userId) {
        log.info("deleting user with id:{}", userId);
        if (userRepository.findById(userId) == null) {
            throw new NotFoundException("No user with id=" + userId + " was found to delete.");
        }
        userRepository.deleteById(userId);
    }

}
