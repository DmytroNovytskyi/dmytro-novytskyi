package com.epam.spring.homework4.service.impl;

import com.epam.spring.homework4.controller.dto.UserActivityDto;
import com.epam.spring.homework4.service.UserActivityService;
import com.epam.spring.homework4.service.exception.UserActivityNotFoundException;
import com.epam.spring.homework4.service.mapper.UserActivityMapper;
import com.epam.spring.homework4.service.model.UserActivity;
import com.epam.spring.homework4.service.repository.UserActivityRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserActivityServiceImpl implements UserActivityService {

    private final UserActivityRepository userActivityRepository;

    @Override
    public List<UserActivityDto> getAll() {
        log.info("reading all userActivities");
        return userActivityRepository.findAll().stream()
                .map(UserActivityMapper.INSTANCE::mapUserActivityDto)
                .sorted(Comparator.comparing(UserActivityDto::getCreationDate))
                .toList();
    }

    @Override
    public UserActivityDto request(UserActivityDto userActivity) {
        log.info("creating request for user:{} for activity:{}",
                userActivity.getUser().getUsername(),
                userActivity.getActivity().getName());
        UserActivity entity = UserActivityMapper.INSTANCE.mapUserActivity(userActivity);
        return UserActivityMapper.INSTANCE.mapUserActivityDto(userActivityRepository.create(entity));
    }

    @Override
    public UserActivityDto update(UserActivityDto userActivity) {
        log.info("updating userActivity with id:{}", userActivity.getId());
        UserActivity stored = userActivityRepository.findById(userActivity.getId());
        if (stored == null) {
            throw new UserActivityNotFoundException();
        }
        UserActivityMapper.INSTANCE.mapPresentFields(stored, userActivity);
        return UserActivityMapper.INSTANCE.mapUserActivityDto(userActivityRepository.update(stored));
    }

    @Override
    public void deleteById(int userActivityId) {
        log.info("deleting userActivity with id:{}", userActivityId);
        if (userActivityRepository.findById(userActivityId) == null) {
            throw new UserActivityNotFoundException();
        }
        userActivityRepository.deleteById(userActivityId);
    }

}
