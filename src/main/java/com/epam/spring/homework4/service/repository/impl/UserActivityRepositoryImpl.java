package com.epam.spring.homework4.service.repository.impl;

import com.epam.spring.homework4.service.model.UserActivity;
import com.epam.spring.homework4.service.model.enums.UserActivityStatus;
import com.epam.spring.homework4.service.repository.UserActivityRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
@Slf4j
public class UserActivityRepositoryImpl implements UserActivityRepository {

    private final List<UserActivity> userActivities = new ArrayList<>();
    private int id;

    @Override
    public UserActivity create(UserActivity entity) {
        id++;
        entity.setId(id);
        entity.setCreationDate(new Timestamp(System.currentTimeMillis()));
        entity.setStatus(UserActivityStatus.PENDING_ASSIGN);
        userActivities.add(entity);
        log.info("successfully created userActivity with id:{}", entity.getId());
        return entity;
    }

    @Override
    public List<UserActivity> findAll() {
        log.info("successfully read userActivities");
        return new ArrayList<>(userActivities);
    }

    @Override
    public UserActivity findById(int id) {
        UserActivity userActivity = userActivities.stream()
                .filter(u -> u.getId() == id)
                .findFirst()
                .orElse(null);
        if (userActivity != null) {
            log.info("found userActivity with id:{}", userActivity.getId());
        } else {
            log.info("could not find userActivity with id:{}", id);
        }
        return userActivity;
    }

    @Override
    public UserActivity update(UserActivity entity) {
        UserActivity userActivity = null;
        Optional<UserActivity> userActivityOptional = userActivities.stream()
                .filter(u -> u.getId() == entity.getId())
                .findFirst();
        if (userActivityOptional.isPresent()) {
            userActivity = userActivityOptional.get();
            userActivity.setUser(entity.getUser());
            userActivity.setActivity(entity.getActivity());
            userActivity.setStatus(entity.getStatus());
            userActivity.setStartTime(entity.getStartTime());
            userActivity.setEndTime(entity.getEndTime());
            log.info("successfully updated userActivity with id:{}", entity.getId());
        }
        return userActivity;
    }

    @Override
    public void deleteById(int id) {
        userActivities.removeIf(u -> u.getId() == id);
        log.info("successfully deleted userActivity with id:{}", id);
    }

}
