package com.epam.spring.homework4.service.repository.impl;

import com.epam.spring.homework4.service.model.User;
import com.epam.spring.homework4.service.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class UserRepositoryImpl implements UserRepository {

    private final List<User> users = new ArrayList<>();
    private int id;

    @Override
    public User create(User entity) {
        id++;
        entity.setId(id);
        users.add(entity);
        log.info("successfully created user with id:{}", entity.getId());
        return entity;
    }

    @Override
    public List<User> findAll() {
        log.info("successfully read users");
        return new ArrayList<>(users);
    }

    @Override
    public User findById(int id) {
        User user = users.stream()
                .filter(a -> a.getId() == id)
                .findFirst()
                .orElse(null);
        if (user != null) {
            log.info("found user with id:{}", user.getId());
        } else {
            log.info("could not find user with id:{}", id);
        }
        return user;
    }

    @Override
    public User update(User entity) {
        User user = null;
        Optional<User> userOptional = users.stream()
                .filter(a -> a.getId() == entity.getId())
                .findFirst();
        if (userOptional.isPresent()) {
            user = userOptional.get();
            user.setUsername(entity.getUsername());
            user.setRole(entity.getRole());
            user.setEmail(entity.getEmail());
            user.setPassword(entity.getPassword());
            user.setStatus(entity.getStatus());
            log.info("successfully updated user with id:{}", entity.getId());
        }
        return user;
    }

    @Override
    public void deleteById(int id) {
        users.removeIf(u -> u.getId() == id);
        log.info("successfully deleted user with id:{}", id);
    }

}
