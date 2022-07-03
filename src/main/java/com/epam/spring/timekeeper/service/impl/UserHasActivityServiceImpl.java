package com.epam.spring.timekeeper.service.impl;

import com.epam.spring.timekeeper.controller.dto.UserHasActivityDto;
import com.epam.spring.timekeeper.service.UserHasActivityService;
import com.epam.spring.timekeeper.service.exception.NotFoundException;
import com.epam.spring.timekeeper.service.mapper.UserHasActivityMapper;
import com.epam.spring.timekeeper.service.model.UserHasActivity;
import com.epam.spring.timekeeper.service.repository.UserHasActivityRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserHasActivityServiceImpl implements UserHasActivityService {

    private final UserHasActivityRepository userHasActivityRepository;

    @Override
    public List<UserHasActivityDto> getAll() {
        log.info("reading all userHasActivities");
        return userHasActivityRepository.findAll().stream()
                .map(UserHasActivityMapper.INSTANCE::mapUserHasActivityDto)
                .sorted(Comparator.comparing(UserHasActivityDto::getCreationDate))
                .toList();
    }

    @Override
    public UserHasActivityDto request(UserHasActivityDto userHasActivity) {
        log.info("creating request for user:{} for activity:{}",
                userHasActivity.getUser().getUsername(),
                userHasActivity.getActivity().getName());
        UserHasActivity entity = UserHasActivityMapper.INSTANCE.mapUserHasActivity(userHasActivity);
        return UserHasActivityMapper.INSTANCE.mapUserHasActivityDto(userHasActivityRepository.create(entity));
    }

    @Override
    public UserHasActivityDto update(UserHasActivityDto userHasActivity) {
        log.info("updating userHasActivity with id:{}", userHasActivity.getId());
        if(userHasActivityRepository.findById(userHasActivity.getId()) == null){
            throw new NotFoundException("No userHasActivity with id=" + userHasActivity.getId() + " was found to update.");
        }
        UserHasActivity entity = UserHasActivityMapper.INSTANCE.mapUserHasActivity(userHasActivity);
        return UserHasActivityMapper.INSTANCE.mapUserHasActivityDto(userHasActivityRepository.update(entity));
    }

    @Override
    public void delete(int userHasActivityId) {
        log.info("deleting userHasActivity with id:{}", userHasActivityId);
        if(userHasActivityRepository.findById(userHasActivityId) == null){
            throw new NotFoundException("No userHasActivity with id=" + userHasActivityId + " was found to delete.");
        }
        userHasActivityRepository.deleteById(userHasActivityId);
    }

}
