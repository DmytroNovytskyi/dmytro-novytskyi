package com.epam.spring.homework6.service.impl;

import com.epam.spring.homework6.controller.dto.UserActivityDto;
import com.epam.spring.homework6.service.UserActivityService;
import com.epam.spring.homework6.service.exception.ActiveUserActivityException;
import com.epam.spring.homework6.service.exception.UserActivityNotFoundException;
import com.epam.spring.homework6.service.exception.UserActivityStatusException;
import com.epam.spring.homework6.service.mapper.UserActivityMapper;
import com.epam.spring.homework6.service.model.UserActivity;
import com.epam.spring.homework6.service.model.enums.UserActivityStatus;
import com.epam.spring.homework6.service.repository.UserActivityRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;

import static com.epam.spring.homework6.service.model.enums.UserActivityStatus.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserActivityServiceImpl implements UserActivityService {

    private final UserActivityRepository userActivityRepository;

    @Override
    public Page<UserActivityDto> getAll(int page, int size, String sortBy, String order) {
        log.info("reading all userActivities");
        Pageable pageable = PageRequest.of(page, size,
                order.equals("desc") ? Sort.by(sortBy).descending()
                        : Sort.by(sortBy).ascending());
        Page<UserActivityDto> userActivities = userActivityRepository.findAll(pageable)
                .map(UserActivityMapper.INSTANCE::mapUserActivityDto);
        log.info("userActivities were successfully read");
        return userActivities;
    }

    @Override
    @Transactional
    public UserActivityDto request(UserActivityDto userActivity) {
        log.info("creating request for user:{} for activity:{}",
                userActivity.getUser().getUsername(),
                userActivity.getActivity().getName());
        UserActivity entity = UserActivityMapper.INSTANCE.mapUserActivity(userActivity);
        userActivityCreateCheck(entity);
        entity.setStatus(PENDING_ASSIGN);
        UserActivity createdUserActivity = userActivityRepository.save(entity);
        log.info("request for user:{} for activity:{} was successfully created with id:{}",
                createdUserActivity.getUser().getUsername(),
                createdUserActivity.getActivity().getName(),
                createdUserActivity.getId());
        return UserActivityMapper.INSTANCE.mapUserActivityDto(createdUserActivity);
    }

    @Override
    @Transactional
    public UserActivityDto update(UserActivityDto userActivity) {
        log.info("updating userActivity with id:{}", userActivity.getId());
        Optional<UserActivity> userActivityOptional =
                userActivityRepository.findById(userActivity.getId());
        if (userActivityOptional.isEmpty()) {
            throw new UserActivityNotFoundException();
        }
        UserActivity mappedUserActivity = UserActivityMapper.INSTANCE.mapUserActivity(userActivity);
        userActivityUpdateCheck(mappedUserActivity);
        UserActivity persistedUserActivity = userActivityOptional.get();
        userActivityStatusChangeLogicCheck(mappedUserActivity.getStatus(), persistedUserActivity);
        UserActivityMapper.INSTANCE.mapPresentFields(persistedUserActivity, mappedUserActivity);
        mapUserActivityTime(persistedUserActivity);
        UserActivity updatedUserActivity = userActivityRepository.save(persistedUserActivity);
        log.info("userActivity with id:{} was successfully updated", updatedUserActivity.getId());
        return UserActivityMapper.INSTANCE.mapUserActivityDto(updatedUserActivity);
    }

    @Override
    @Transactional
    public void deleteById(int userActivityId) {
        log.info("deleting userActivity with id:{}", userActivityId);
        if (!userActivityRepository.existsById(userActivityId)) {
            throw new UserActivityNotFoundException();
        }
        userActivityRepository.deleteById(userActivityId);
        log.info("userActivity with id:{} was successfully deleted", userActivityId);
    }

    private void userActivityCreateCheck(UserActivity userActivity) {
        if (userActivityRepository.existsByUserAndActivityAndStatus(userActivity.getUser(),
                userActivity.getActivity(), PENDING_ASSIGN)
                || userActivityRepository.existsByUserAndActivityAndStatus(userActivity.getUser(),
                userActivity.getActivity(), UserActivityStatus.ASSIGNED)
                || userActivityRepository.existsByUserAndActivityAndStatus(userActivity.getUser(),
                userActivity.getActivity(), UserActivityStatus.IN_PROGRESS)
                || userActivityRepository.existsByUserAndActivityAndStatus(userActivity.getUser(),
                userActivity.getActivity(), UserActivityStatus.PENDING_ABORT)) {
            throw new ActiveUserActivityException();
        }
    }

    private void userActivityUpdateCheck(UserActivity userActivity) {
        UserActivityStatus status = userActivity.getStatus();
        if ((status == PENDING_ASSIGN || status == ASSIGNED || status == IN_PROGRESS || status == PENDING_ABORT)
                && (userActivityRepository.existsByUserAndActivityAndStatusAndIdIsNot(userActivity.getUser(),
                userActivity.getActivity(), PENDING_ASSIGN, userActivity.getId())
                || userActivityRepository.existsByUserAndActivityAndStatusAndIdIsNot(userActivity.getUser(),
                userActivity.getActivity(), UserActivityStatus.ASSIGNED, userActivity.getId())
                || userActivityRepository.existsByUserAndActivityAndStatusAndIdIsNot(userActivity.getUser(),
                userActivity.getActivity(), UserActivityStatus.IN_PROGRESS, userActivity.getId())
                || userActivityRepository.existsByUserAndActivityAndStatusAndIdIsNot(userActivity.getUser(),
                userActivity.getActivity(), UserActivityStatus.PENDING_ABORT, userActivity.getId()))) {
            throw new ActiveUserActivityException();
        }
    }

    private void userActivityStatusChangeLogicCheck(UserActivityStatus newStatus,
                                                       UserActivity persistedUserActivity) {
        UserActivityStatus presentStatus = persistedUserActivity.getStatus();
        if (presentStatus != newStatus
                && (presentStatus == ASSIGNED && newStatus != IN_PROGRESS && newStatus != PENDING_ABORT
                || presentStatus == IN_PROGRESS && newStatus != COMPLETED && newStatus != PENDING_ABORT
                || presentStatus == PENDING_ASSIGN && newStatus != ASSIGNED && newStatus != DECLINED
                || presentStatus == PENDING_ABORT && persistedUserActivity.getStartTime() == null
                && newStatus != ABORTED && newStatus != ASSIGNED
                || presentStatus == PENDING_ABORT && persistedUserActivity.getStartTime() != null
                && newStatus != ABORTED && newStatus != IN_PROGRESS
                || presentStatus == DECLINED
                || presentStatus == COMPLETED
                || presentStatus == ABORTED)) {
            throw new UserActivityStatusException();
        }
    }

    private void mapUserActivityTime(UserActivity userActivity) {
        if (userActivity.getStatus() == IN_PROGRESS && userActivity.getStartTime() == null) {
            userActivity.setStartTime(Timestamp.valueOf(LocalDateTime.now()));
        }
        if (userActivity.getStatus() == COMPLETED) {
            userActivity.setEndTime(Timestamp.valueOf(LocalDateTime.now()));
        }
    }

}
