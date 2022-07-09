package com.epam.spring.homework5.service.repository;

import com.epam.spring.homework5.service.model.User;

import java.util.List;

public interface UserRepository {

    User create(User entity);

    List<User> findAll();

    User findById(int id);

    User update(User entity);

    void deleteById(int id);

}
