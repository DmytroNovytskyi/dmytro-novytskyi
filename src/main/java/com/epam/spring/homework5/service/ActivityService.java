package com.epam.spring.homework5.service;

import com.epam.spring.homework5.controller.dto.ActivityDto;

import java.util.List;

public interface ActivityService {

    List<ActivityDto> getAll();

    ActivityDto create(ActivityDto activity);

    ActivityDto update(ActivityDto activity);

    void delete(int activityId);

}
