package com.epam.spring.homework5.service.impl;

import com.epam.spring.homework5.controller.dto.ActivityDto;
import com.epam.spring.homework5.service.ActivityService;
import com.epam.spring.homework5.service.exception.ActivityAlreadyExistsException;
import com.epam.spring.homework5.service.exception.ActivityNotFoundException;
import com.epam.spring.homework5.service.exception.CategoryNotFoundException;
import com.epam.spring.homework5.service.mapper.ActivityMapper;
import com.epam.spring.homework5.service.model.Activity;
import com.epam.spring.homework5.service.model.Category;
import com.epam.spring.homework5.service.repository.ActivityRepository;
import com.epam.spring.homework5.service.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ActivityServiceImpl implements ActivityService {

    private final ActivityRepository activityRepository;
    private final CategoryRepository categoryRepository;

    public Page<ActivityDto> getAll(int page, int size, String sortBy, String order) {
        log.info("reading activities");
        Pageable pageable = PageRequest.of(page, size,
                order.equals("desc") ? Sort.by(sortBy).descending()
                        : Sort.by(sortBy).ascending());
        Page<ActivityDto> activities = activityRepository.findAll(pageable)
                .map(ActivityMapper.INSTANCE::mapActivityDto);
        log.info("activities were successfully read");
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
        Activity mappedActivity = ActivityMapper.INSTANCE.mapActivity(activity);
        Category category = mappedActivity.getCategory();
        if (category != null && category.getId() != null
                && !Objects.equals(category.getId(), persistedActivity.getCategory().getId())) {
            Optional<Category> categoryOptional = categoryRepository.findById(category.getId());
            if (categoryOptional.isEmpty()) {
                throw new CategoryNotFoundException();
            }
            persistedActivity.setCategory(categoryOptional.get());
        }
        ActivityMapper.INSTANCE.mapPresentFields(persistedActivity,
                mappedActivity);
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
