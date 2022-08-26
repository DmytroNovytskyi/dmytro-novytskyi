package com.epam.spring.homework3.service.repository.impl;

import com.epam.spring.homework3.service.model.Activity;
import com.epam.spring.homework3.service.repository.ActivityRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
@Slf4j
public class ActivityRepositoryImpl implements ActivityRepository {

    private final List<Activity> activities = new ArrayList<>();
    private int id;

    @Override
    public Activity create(Activity entity) {
        id++;
        entity.setId(id);
        activities.add(entity);
        log.info("successfully created activity with id:{}", entity.getId());
        return entity;
    }

    @Override
    public List<Activity> findAll() {
        log.info("successfully read activities");
        return new ArrayList<>(activities);
    }

    @Override
    public Activity findById(int id) {
        Activity activity = activities.stream()
                .filter(a -> a.getId() == id)
                .findFirst()
                .orElse(null);
        if (activity != null) {
            log.info("found activity with id:{}", activity.getId());
        } else {
            log.info("could not find activity with id:{}", id);
        }
        return activity;
    }

    @Override
    public Activity update(Activity entity) {
        Activity activity = null;
        Optional<Activity> activityOptional = activities.stream()
                .filter(a -> a.getId() == id)
                .findFirst();
        if (activityOptional.isPresent()) {
            activity = activityOptional.get();
            activity.setCategory(entity.getCategory());
            activity.setName(entity.getName());
            activity.setStatus(entity.getStatus());
            activity.setDescription(entity.getDescription());
            log.info("successfully updated activity with id:{}", entity.getId());
        }
        return activity;
    }

    @Override
    public void deleteById(int id) {
        activities.removeIf(a -> a.getId() == id);
        log.info("successfully deleted activity with id:{}", id);
    }

}
