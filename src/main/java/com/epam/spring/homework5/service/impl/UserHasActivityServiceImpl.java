package com.epam.spring.homework5.service.impl;

import com.epam.spring.homework5.controller.dto.UserHasActivityDto;
import com.epam.spring.homework5.service.UserHasActivityService;
import com.epam.spring.homework5.service.exception.ActiveUserHasActivityException;
import com.epam.spring.homework5.service.exception.UserHasActivityNotFoundException;
import com.epam.spring.homework5.service.exception.UserHasActivityStatusException;
import com.epam.spring.homework5.service.mapper.UserHasActivityMapper;
import com.epam.spring.homework5.service.model.UserHasActivity;
import com.epam.spring.homework5.service.model.enums.UserHasActivityStatus;
import com.epam.spring.homework5.service.repository.UserHasActivityRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import static com.epam.spring.homework5.service.model.enums.UserHasActivityStatus.*;

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
    @Transactional
    public UserHasActivityDto request(UserHasActivityDto userHasActivity) {
        log.info("creating request for user:{} for activity:{}",
                userHasActivity.getUser().getUsername(),
                userHasActivity.getActivity().getName());
        UserHasActivity entity = UserHasActivityMapper.INSTANCE.mapUserHasActivity(userHasActivity);
        userHasActivityCreateCheck(entity);
        entity.setStatus(PENDING_ASSIGN);
        UserHasActivity createdUserHasActivity = userHasActivityRepository.save(entity);
        log.info("request for user:{} for activity:{} was successfully created with id:{}",
                createdUserHasActivity.getUser().getUsername(),
                createdUserHasActivity.getActivity().getName(),
                createdUserHasActivity.getId());
        return UserHasActivityMapper.INSTANCE.mapUserHasActivityDto(createdUserHasActivity);
    }

    @Override
    @Transactional
    public UserHasActivityDto update(UserHasActivityDto userHasActivity) {
        log.info("updating userHasActivity with id:{}", userHasActivity.getId());
        Optional<UserHasActivity> userHasActivityOptional =
                userHasActivityRepository.findById(userHasActivity.getId());
        if (userHasActivityOptional.isEmpty()) {
            throw new UserHasActivityNotFoundException();
        }
        UserHasActivity mappedUserHasActivity = UserHasActivityMapper.INSTANCE.mapUserHasActivity(userHasActivity);
        userHasActivityUpdateCheck(mappedUserHasActivity);
        UserHasActivity persistedUserHasActivity = userHasActivityOptional.get();
        userHasActivityStatusChangeLogicCheck(mappedUserHasActivity.getStatus(), persistedUserHasActivity);
        UserHasActivityMapper.INSTANCE.mapPresentFields(persistedUserHasActivity, mappedUserHasActivity);
        mapUserHasActivityTime(persistedUserHasActivity);
        UserHasActivity updatedUserHasActivity = userHasActivityRepository.save(persistedUserHasActivity);
        log.info("userHasActivity with id:{} was successfully updated", updatedUserHasActivity.getId());
        return UserHasActivityMapper.INSTANCE.mapUserHasActivityDto(updatedUserHasActivity);
    }

    @Override
    @Transactional
    public void delete(int userHasActivityId) {
        log.info("deleting userHasActivity with id:{}", userHasActivityId);
        if (!userHasActivityRepository.existsById(userHasActivityId)) {
            throw new UserHasActivityNotFoundException();
        }
        userHasActivityRepository.deleteById(userHasActivityId);
        log.info("userHasActivity with id:{} was successfully deleted", userHasActivityId);
    }

    private void userHasActivityCreateCheck(UserHasActivity userHasActivity) {
        if (userHasActivityRepository.existsByUserAndActivityAndStatus(userHasActivity.getUser(),
                userHasActivity.getActivity(), PENDING_ASSIGN)
                || userHasActivityRepository.existsByUserAndActivityAndStatus(userHasActivity.getUser(),
                userHasActivity.getActivity(), UserHasActivityStatus.ASSIGNED)
                || userHasActivityRepository.existsByUserAndActivityAndStatus(userHasActivity.getUser(),
                userHasActivity.getActivity(), UserHasActivityStatus.IN_PROGRESS)
                || userHasActivityRepository.existsByUserAndActivityAndStatus(userHasActivity.getUser(),
                userHasActivity.getActivity(), UserHasActivityStatus.PENDING_ABORT)) {
            throw new ActiveUserHasActivityException();
        }
    }

    private void userHasActivityUpdateCheck(UserHasActivity userHasActivity) {
        UserHasActivityStatus status = userHasActivity.getStatus();
        if ((status == PENDING_ASSIGN || status == ASSIGNED || status == IN_PROGRESS || status == PENDING_ABORT)
                && (userHasActivityRepository.existsByUserAndActivityAndStatusAndIdIsNot(userHasActivity.getUser(),
                userHasActivity.getActivity(), PENDING_ASSIGN, userHasActivity.getId())
                || userHasActivityRepository.existsByUserAndActivityAndStatusAndIdIsNot(userHasActivity.getUser(),
                userHasActivity.getActivity(), UserHasActivityStatus.ASSIGNED, userHasActivity.getId())
                || userHasActivityRepository.existsByUserAndActivityAndStatusAndIdIsNot(userHasActivity.getUser(),
                userHasActivity.getActivity(), UserHasActivityStatus.IN_PROGRESS, userHasActivity.getId())
                || userHasActivityRepository.existsByUserAndActivityAndStatusAndIdIsNot(userHasActivity.getUser(),
                userHasActivity.getActivity(), UserHasActivityStatus.PENDING_ABORT, userHasActivity.getId()))) {
            throw new ActiveUserHasActivityException();
        }
    }

    private void userHasActivityStatusChangeLogicCheck(UserHasActivityStatus newStatus,
                                                       UserHasActivity persistedUserHasActivity) {
        UserHasActivityStatus presentStatus = persistedUserHasActivity.getStatus();
        if (presentStatus != newStatus
                && (presentStatus == ASSIGNED && newStatus != IN_PROGRESS && newStatus != PENDING_ABORT
                || presentStatus == IN_PROGRESS && newStatus != COMPLETED && newStatus != PENDING_ABORT
                || presentStatus == PENDING_ASSIGN && newStatus != ASSIGNED && newStatus != DECLINED
                || presentStatus == PENDING_ABORT && persistedUserHasActivity.getStartTime() == null
                && newStatus != ABORTED && newStatus != ASSIGNED
                || presentStatus == PENDING_ABORT && persistedUserHasActivity.getStartTime() != null
                && newStatus != ABORTED && newStatus != IN_PROGRESS
                || presentStatus == DECLINED
                || presentStatus == COMPLETED
                || presentStatus == ABORTED)) {
            throw new UserHasActivityStatusException();
        }
    }

    private void mapUserHasActivityTime(UserHasActivity userHasActivity) {
        if (userHasActivity.getStatus() == IN_PROGRESS && userHasActivity.getStartTime() == null) {
            userHasActivity.setStartTime(Timestamp.valueOf(LocalDateTime.now()));
        }
        if (userHasActivity.getStatus() == COMPLETED) {
            userHasActivity.setEndTime(Timestamp.valueOf(LocalDateTime.now()));
        }
    }

}
