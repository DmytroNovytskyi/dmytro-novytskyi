package com.epam.spring.timekeeper.service;

import com.epam.spring.timekeeper.controller.dto.UserHasActivityDto;

import java.util.List;

public interface UserHasActivityService {

    List<UserHasActivityDto> getAll();

    UserHasActivityDto request(UserHasActivityDto userHasActivity);

    UserHasActivityDto update(UserHasActivityDto userHasActivity);

    void delete(int userHasActivityId);

}
