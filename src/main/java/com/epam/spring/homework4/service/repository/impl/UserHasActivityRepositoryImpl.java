package com.epam.spring.homework4.service.repository.impl;

import com.epam.spring.homework4.service.model.UserHasActivity;
import com.epam.spring.homework4.service.model.enums.UserHasActivityStatus;
import com.epam.spring.homework4.service.repository.UserHasActivityRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class UserHasActivityRepositoryImpl implements UserHasActivityRepository {

    private final List<UserHasActivity> userHasActivities = new ArrayList<>();
    private int id;

    @Override
    public UserHasActivity create(UserHasActivity entity) {
        id++;
        entity.setId(id);
        entity.setCreationDate(new Timestamp(System.currentTimeMillis()));
        entity.setStatus(UserHasActivityStatus.PENDING_ASSIGN);
        userHasActivities.add(entity);
        log.info("successfully created userHasActivity with id:{}", entity.getId());
        return entity;
    }

    @Override
    public List<UserHasActivity> findAll() {
        log.info("successfully read userHasActivities");
        return new ArrayList<>(userHasActivities);
    }

    @Override
    public UserHasActivity findById(int id) {
        UserHasActivity userHasActivity = userHasActivities.stream()
                .filter(u -> u.getId() == id)
                .findFirst()
                .orElse(null);
        if (userHasActivity != null) {
            log.info("found userHasActivity with id:{}", userHasActivity.getId());
        } else {
            log.info("could not find userHasActivity with id:{}", id);
        }
        return userHasActivity;
    }

    @Override
    public UserHasActivity update(UserHasActivity entity) {
        UserHasActivity userHasActivity = null;
        Optional<UserHasActivity> userHasActivityOptional = userHasActivities.stream()
                .filter(u -> u.getId() == entity.getId())
                .findFirst();
        if (userHasActivityOptional.isPresent()) {
            userHasActivity = userHasActivityOptional.get();
            userHasActivity.setUser(entity.getUser());
            userHasActivity.setActivity(entity.getActivity());
            userHasActivity.setStatus(entity.getStatus());
            userHasActivity.setStartTime(entity.getStartTime());
            userHasActivity.setEndTime(entity.getEndTime());
            log.info("successfully updated userHasActivity with id:{}", entity.getId());
        }
        return userHasActivity;
    }

    @Override
    public void deleteById(int id) {
        userHasActivities.removeIf(u -> u.getId() == id);
        log.info("successfully deleted userHasActivity with id:{}", id);
    }

}
