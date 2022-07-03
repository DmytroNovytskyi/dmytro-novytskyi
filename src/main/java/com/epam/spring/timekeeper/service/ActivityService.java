package com.epam.spring.timekeeper.service;

import com.epam.spring.timekeeper.controller.dto.ActivityDto;

import java.util.List;

public interface ActivityService {

    List<ActivityDto> getAll();

    ActivityDto create(ActivityDto activity);

    ActivityDto update(ActivityDto activity);

    void delete(int activityId);

}
