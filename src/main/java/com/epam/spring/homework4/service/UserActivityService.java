package com.epam.spring.homework4.service;

import com.epam.spring.homework4.controller.dto.UserActivityDto;

import java.util.List;

public interface UserActivityService {

    List<UserActivityDto> getAll();

    UserActivityDto request(UserActivityDto userActivity);

    UserActivityDto update(UserActivityDto userActivity);

    void deleteById(int userActivityId);

}
