package com.epam.spring.homework6.service;

import com.epam.spring.homework6.controller.dto.ActivityDto;
import org.springframework.data.domain.Page;

public interface ActivityService {

    Page<ActivityDto> getAll(int page, int size, String sortBy, String order);

    ActivityDto create(ActivityDto activity);

    ActivityDto update(ActivityDto activity);

    void delete(int activityId);

}
