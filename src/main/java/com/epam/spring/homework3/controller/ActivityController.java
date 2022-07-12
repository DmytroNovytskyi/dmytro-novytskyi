package com.epam.spring.homework3.controller;

import com.epam.spring.homework3.controller.dto.ActivityDto;
import com.epam.spring.homework3.service.ActivityService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ActivityController {

    private final ActivityService activityService;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/activity")
    public List<ActivityDto> getAllActivities() {
        log.info("accepted request to get all activities");
        return activityService.getAll();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/activity")
    public ActivityDto createActivity(@RequestBody ActivityDto activity) {
        log.info("accepted request to create activity with name:{}", activity.getName());
        return activityService.create(activity);
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/activity")
    public ActivityDto updateActivity(@RequestBody ActivityDto activity) {
        log.info("accepted request to update activity with id:{}", activity.getId());
        return activityService.update(activity);
    }

    @DeleteMapping("/activity/{activityId}")
    public ResponseEntity<Void> deleteActivity(@PathVariable int activityId) {
        log.info("accepted request to delete activity with id:{}", activityId);
        activityService.delete(activityId);
        return ResponseEntity.noContent().build();
    }

}
