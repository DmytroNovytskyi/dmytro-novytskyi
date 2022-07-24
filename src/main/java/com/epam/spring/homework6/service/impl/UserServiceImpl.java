package com.epam.spring.homework6.service.impl;

import com.epam.spring.homework6.controller.dto.UserDto;
import com.epam.spring.homework6.service.UserService;
import com.epam.spring.homework6.service.exception.UserAlreadyExistsException;
import com.epam.spring.homework6.service.exception.UserNotFoundException;
import com.epam.spring.homework6.service.mapper.UserMapper;
import com.epam.spring.homework6.service.model.User;
import com.epam.spring.homework6.service.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public Page<UserDto> getAll(int page, int size, String sortBy, String order) {
        log.info("reading users");
        Pageable pageable = PageRequest.of(page, size,
                order.equals("desc") ? Sort.by(sortBy).descending()
                        : Sort.by(sortBy).ascending());
        Page<UserDto> users = userRepository.findAll(pageable)
                .map(UserMapper.INSTANCE::mapUserDto);
        log.info("users were successfully read");
        return users;
    }

    @Override
    @Transactional
    public UserDto create(UserDto user) {
        log.info("creating user with name:{}", user.getUsername());
        if (userRepository.existsByUsernameOrEmail(user.getUsername(), user.getEmail())) {
            throw new UserAlreadyExistsException();
        }
        User createdUser = userRepository.save(UserMapper.INSTANCE.mapUser(user));
        log.info("user with username:{} was successfully created with id:{}",
                createdUser.getUsername(), createdUser.getId());
        return UserMapper.INSTANCE.mapUserDto(createdUser);
    }

    @Override
    @Transactional
    public UserDto update(UserDto user) {
        log.info("updating user with id:{}", user.getId());
        Optional<User> userOptional = userRepository.findById(user.getId());
        if (userOptional.isEmpty()) {
            throw new UserNotFoundException();
        }
        if (userRepository.existsByUsernameOrEmailAndIdIsNot(user.getUsername(),
                user.getEmail(), user.getId())) {
            throw new UserAlreadyExistsException();
        }
        User persistedUser = userOptional.get();
        UserMapper.INSTANCE.mapPresentFields(persistedUser, UserMapper.INSTANCE.mapUser(user));
        User updatedUser = userRepository.save(persistedUser);
        log.info("user with id:{} was successfully updated", updatedUser.getId());
        return UserMapper.INSTANCE.mapUserDto(updatedUser);
    }

    @Override
    @Transactional
    public void delete(int userId) {
        log.info("deleting user with id:{}", userId);
        if (!userRepository.existsById(userId)) {
            throw new UserNotFoundException();
        }
        userRepository.deleteById(userId);
        log.info("activity with id:{} was successfully deleted", userId);
    }

}
