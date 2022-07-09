package com.epam.spring.homework5.service;

import com.epam.spring.homework5.controller.dto.UserHasActivityDto;

import java.util.List;

public interface UserHasActivityService {

    List<UserHasActivityDto> getAll();

    UserHasActivityDto request(UserHasActivityDto userHasActivity);

    UserHasActivityDto update(UserHasActivityDto userHasActivity);

    void delete(int userHasActivityId);

}
