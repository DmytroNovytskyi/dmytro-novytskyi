package com.epam.spring.homework3.service;

import com.epam.spring.homework3.controller.dto.ActivityDto;

import java.util.List;

public interface ActivityService {

    List<ActivityDto> getAll();

    ActivityDto create(ActivityDto activity);

    ActivityDto update(ActivityDto activity);

    void deleteById(int activityId);

}
