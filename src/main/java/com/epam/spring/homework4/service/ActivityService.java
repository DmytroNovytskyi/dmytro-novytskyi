package com.epam.spring.homework4.service;

import com.epam.spring.homework4.controller.dto.ActivityDto;

import java.util.List;

public interface ActivityService {

    List<ActivityDto> getAll();

    ActivityDto create(ActivityDto activity);

    ActivityDto update(ActivityDto activity);

    void delete(int activityId);

}
