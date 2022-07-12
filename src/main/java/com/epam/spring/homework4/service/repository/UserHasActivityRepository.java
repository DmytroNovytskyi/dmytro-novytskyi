package com.epam.spring.homework4.service.repository;

import com.epam.spring.homework4.service.model.UserHasActivity;

import java.util.List;

public interface UserHasActivityRepository {

    UserHasActivity create(UserHasActivity entity);

    List<UserHasActivity> findAll();

    UserHasActivity findById(int id);

    UserHasActivity update(UserHasActivity entity);

    void deleteById(int id);

}
