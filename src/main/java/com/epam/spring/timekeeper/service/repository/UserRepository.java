package com.epam.spring.timekeeper.service.repository;

import com.epam.spring.timekeeper.service.model.User;

import java.util.List;

public interface UserRepository {

    User create(User entity);

    List<User> findAll();

    User findById(int id);

    User update(User entity);

    void deleteById(int id);

}
