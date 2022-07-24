package com.epam.spring.homework6.controller;

import com.epam.spring.homework6.controller.api.ActivityApi;
import com.epam.spring.homework6.controller.dto.ActivityDto;
import com.epam.spring.homework6.service.ActivityService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ActivityController implements ActivityApi {

    private final ActivityService activityService;

    @Override
    public Page<ActivityDto> getSortedPagedActivities(int page, int size, String sortBy, String order) {
        log.info("accepted request to get activities");
        return activityService.getAll(page, size, sortBy, order);
    }

    @Override
    public ActivityDto createActivity(ActivityDto activity) {
        log.info("accepted request to create activity with name:{}", activity.getName());
        return activityService.create(activity);
    }

    @Override
    public ActivityDto updateActivity(ActivityDto activity) {
        log.info("accepted request to update activity with id:{}", activity.getId());
        return activityService.update(activity);
    }

    @Override
    public ResponseEntity<Void> deleteActivity(int activityId) {
        log.info("accepted request to delete activity with id:{}", activityId);
        activityService.delete(activityId);
        return ResponseEntity.noContent().build();
    }

}
