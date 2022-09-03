package com.epam.spring.homework4.service.repository;

import com.epam.spring.homework4.service.model.UserActivity;

import java.util.List;

public interface UserActivityRepository {

    UserActivity create(UserActivity entity);

    List<UserActivity> findAll();

    UserActivity findById(int id);

    UserActivity update(UserActivity entity);

    void deleteById(int id);

}
