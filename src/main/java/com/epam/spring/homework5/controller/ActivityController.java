package com.epam.spring.homework5.controller;

import com.epam.spring.homework5.controller.api.ActivityApi;
import com.epam.spring.homework5.controller.dto.ActivityDto;
import com.epam.spring.homework5.service.ActivityService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ActivityController implements ActivityApi {

    private final ActivityService activityService;

    @Override
    public List<ActivityDto> getAllActivities() {
        log.info("accepted request to get all activities");
        return activityService.getAll();
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
