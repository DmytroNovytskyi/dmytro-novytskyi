package com.epam.spring.homework5.service.repository;

import com.epam.spring.homework5.service.model.Activity;

import java.util.List;


public interface ActivityRepository {

    Activity create(Activity entity);

    List<Activity> findAll();

    Activity findById(int id);

    Activity update(Activity entity);

    void deleteById(int id);

}
