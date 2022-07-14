package com.epam.spring.homework5.service.impl;

import com.epam.spring.homework5.controller.dto.ActivityDto;
import com.epam.spring.homework5.service.ActivityService;
import com.epam.spring.homework5.service.exception.ActivityAlreadyExistsException;
import com.epam.spring.homework5.service.exception.ActivityNotFoundException;
import com.epam.spring.homework5.service.mapper.ActivityMapper;
import com.epam.spring.homework5.service.model.Activity;
import com.epam.spring.homework5.service.repository.ActivityRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ActivityServiceImpl implements ActivityService {

    private final ActivityRepository activityRepository;

    public List<ActivityDto> getAll() {
        log.info("reading all activities");
        List<ActivityDto> activities = activityRepository.findAll().stream()
                .map(ActivityMapper.INSTANCE::mapActivityDto)
                .sorted(Comparator.comparing(ActivityDto::getName))
                .toList();
        log.info("all activities were successfully read");
        return activities;
    }

    @Override
    @Transactional
    public ActivityDto create(ActivityDto activity) {
        log.info("creating activity with name:{}", activity.getName());
        if (activityRepository.existsByName(activity.getName())) {
            throw new ActivityAlreadyExistsException();
        }
        Activity createdActivity = activityRepository.save(ActivityMapper.INSTANCE.mapActivity(activity));
        log.info("activity with name:{} was successfully created with id:{}",
                createdActivity.getName(), createdActivity.getId());
        return ActivityMapper.INSTANCE.mapActivityDto(createdActivity);
    }

    @Override
    @Transactional
    public ActivityDto update(ActivityDto activity) {
        log.info("updating activity with id:{}", activity.getId());
        Optional<Activity> activityOptional = activityRepository.findById(activity.getId());
        if (activityOptional.isEmpty()) {
            throw new ActivityNotFoundException();
        }
        if (activityRepository.existsByNameAndIdIsNot(activity.getName(), activity.getId())) {
            throw new ActivityAlreadyExistsException();
        }
        Activity persistedActivity = activityOptional.get();
        ActivityMapper.INSTANCE.mapPresentFields(persistedActivity,
                ActivityMapper.INSTANCE.mapActivity(activity));
        Activity updatedActivity = activityRepository.save(persistedActivity);
        log.info("activity with id:{} was successfully updated", updatedActivity.getId());
        return ActivityMapper.INSTANCE.mapActivityDto(updatedActivity);
    }

    @Override
    @Transactional
    public void delete(int activityId) {
        log.info("deleting activity with id:{}", activityId);
        if (!activityRepository.existsById(activityId)) {
            throw new ActivityNotFoundException();
        }
        activityRepository.deleteById(activityId);
        log.info("activity with id:{} was successfully deleted", activityId);
    }

}
