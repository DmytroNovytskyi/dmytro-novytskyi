package com.epam.spring.homework4.service.impl;

import com.epam.spring.homework4.controller.dto.ActivityDto;
import com.epam.spring.homework4.service.ActivityService;
import com.epam.spring.homework4.service.exception.ActivityNotFoundException;
import com.epam.spring.homework4.service.mapper.ActivityMapper;
import com.epam.spring.homework4.service.model.Activity;
import com.epam.spring.homework4.service.model.UserHasActivity;
import com.epam.spring.homework4.service.repository.ActivityRepository;
import com.epam.spring.homework4.service.repository.UserHasActivityRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ActivityServiceImpl implements ActivityService {

    private final ActivityRepository activityRepository;
    private final UserHasActivityRepository userHasActivityRepository;

    public List<ActivityDto> getAll() {
        log.info("reading all activities");
        List<UserHasActivity> userHasActivities = userHasActivityRepository.findAll();
        return activityRepository.findAll().stream()
                .map(a -> ActivityMapper.INSTANCE.mapActivityDto(a, userHasActivities))
                .sorted(Comparator.comparing(ActivityDto::getName))
                .toList();
    }

    @Override
    public ActivityDto create(ActivityDto activity) {
        log.info("creating activity with name:{}", activity.getName());
        Activity created = activityRepository.create(ActivityMapper.INSTANCE.mapActivity(activity));
        return processActivity(created);
    }

    @Override
    public ActivityDto update(ActivityDto activity) {
        log.info("updating activity with id:{}", activity.getId());
        Activity stored = activityRepository.findById(activity.getId());
        if (stored == null) {
            throw new ActivityNotFoundException();
        }
        ActivityMapper.INSTANCE.mapPresentFields(stored, activity);
        Activity updated = activityRepository.update(stored);
        return processActivity(updated);
    }

    @Override
    public void delete(int activityId) {
        log.info("deleting activity with id:{}", activityId);
        if (activityRepository.findById(activityId) == null) {
            throw new ActivityNotFoundException();
        }
        activityRepository.deleteById(activityId);
    }

    private ActivityDto processActivity(Activity activity) {
        List<UserHasActivity> userHasActivities = userHasActivityRepository.findAll();
        return ActivityMapper.INSTANCE.mapActivityDto(activity, userHasActivities);
    }

}
