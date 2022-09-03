package com.epam.spring.homework5.service;

import com.epam.spring.homework5.controller.dto.UserActivityDto;
import org.springframework.data.domain.Page;

public interface UserActivityService {

    Page<UserActivityDto> getAll(int page, int size, String sortBy, String order);

    UserActivityDto request(UserActivityDto userActivity);

    UserActivityDto update(UserActivityDto userActivity);

    void deleteById(int userActivityId);

}
