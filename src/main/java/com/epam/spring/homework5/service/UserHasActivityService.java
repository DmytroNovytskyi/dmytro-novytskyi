package com.epam.spring.homework5.service;

import com.epam.spring.homework5.controller.dto.UserHasActivityDto;
import org.springframework.data.domain.Page;

public interface UserHasActivityService {

    Page<UserHasActivityDto> getAll(int page, int size, String sortBy, String order);

    UserHasActivityDto request(UserHasActivityDto userHasActivity);

    UserHasActivityDto update(UserHasActivityDto userHasActivity);

    void deleteById(int userHasActivityId);

}
