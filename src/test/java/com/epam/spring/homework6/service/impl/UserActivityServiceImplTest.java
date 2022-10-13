package com.epam.spring.homework6.service.impl;

import com.epam.spring.homework6.controller.dto.UserActivityDto;
import com.epam.spring.homework6.service.exception.ActiveUserActivityException;
import com.epam.spring.homework6.service.exception.UserActivityNotFoundException;
import com.epam.spring.homework6.service.exception.UserActivityStatusException;
import com.epam.spring.homework6.service.mapper.UserActivityMapper;
import com.epam.spring.homework6.service.model.UserActivity;
import com.epam.spring.homework6.service.model.enums.UserActivityStatus;
import com.epam.spring.homework6.service.repository.UserActivityRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static com.epam.spring.homework6.util.CommonTestData.*;
import static com.epam.spring.homework6.util.UserActivityTestData.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserActivityServiceImplTest {

    @InjectMocks
    UserActivityServiceImpl userActivityService;

    @Mock
    UserActivityRepository userActivityRepository;

    @Test
    void givenPageableData_whenFindAll_thenReturnPageOfUserActivities() {
        Pageable pageable = createPageable();
        List<UserActivity> userActivities = createUserActivityList();
        Page<UserActivity> userActivityPage =
                new PageImpl<>(userActivities, pageable, userActivities.size());
        Page<UserActivityDto> expectedPage = userActivityPage
                .map(UserActivityMapper.INSTANCE::mapUserActivityDto);

        when(userActivityRepository.findAll(pageable)).thenReturn(userActivityPage);

        Page<UserActivityDto> actualPage = userActivityService.getAll(PAGE, SIZE, SORT_BY, ORDER);

        assertThat(actualPage.getSize(), is(SIZE));
        assertThat(actualPage.getPageable(), is(pageable));
        assertThat(actualPage, is(expectedPage));
        verify(userActivityRepository, times(1)).findAll(pageable);
    }

    @Test
    void givenUserActivityDtoWithActiveCombination_whenCreate_thenThrowActiveUserActivityException() {
        UserActivityDto userActivityDto = createUserActivityDto();
        UserActivity userActivity = createUserActivity();

        when(userActivityRepository.existsByUserAndActivityAndStatus(userActivity.getUser(),
                userActivity.getActivity(),
                UserActivityStatus.PENDING_ASSIGN))
                .thenReturn(true);

        assertThatExceptionOfType(ActiveUserActivityException.class)
                .isThrownBy(() -> userActivityService.request(userActivityDto))
                .withMessage("Request for this user and activity is already active!");
        verify(userActivityRepository, times(1))
                .existsByUserAndActivityAndStatus(userActivity.getUser(),
                        userActivity.getActivity(),
                        UserActivityStatus.PENDING_ASSIGN);
    }

    @Test
    void givenValidUserActivityDto_whenCreate_thenReturnCreatedUserActivityDto() {
        UserActivityDto userActivityDto = createUserActivityDto();
        UserActivity userActivity = createUserActivity();

        when(userActivityRepository
                .save(UserActivityMapper.INSTANCE.mapUserActivity(userActivityDto)))
                .thenReturn(userActivity);

        UserActivityDto actualUserActivityDto = userActivityService.request(userActivityDto);
        UserActivityDto expectedUserActivityDto = createUserActivityDto();
        expectedUserActivityDto.setId(userActivity.getId());

        assertThat(actualUserActivityDto, is(expectedUserActivityDto));
        verify(userActivityRepository, times(1))
                .save(UserActivityMapper.INSTANCE.mapUserActivity(userActivityDto));
    }

    @Test
    void givenUserActivityDtoWithInvalidId_whenUpdate_thenThrowUserActivityNotFoundException() {
        UserActivityDto userActivityDto = createUserActivityDto();
        userActivityDto.setId(ID);

        when(userActivityRepository.findById(ID)).thenReturn(Optional.empty());

        assertThatExceptionOfType(UserActivityNotFoundException.class)
                .isThrownBy(() -> userActivityService.update(userActivityDto))
                .withMessage("UserActivity was not found!");
        verify(userActivityRepository, times(1)).findById(ID);
        verify(userActivityRepository, times(0)).save(any());
    }

    @Test
    void givenUserActivityDtoWithActiveCombination_whenUpdate_thenThrowActiveUserActivityException() {
        UserActivityDto userActivityDto = createUserActivityDto();
        userActivityDto.setId(ID);
        UserActivity userActivity = createUserActivity();

        when(userActivityRepository.findById(ID)).thenReturn(Optional.of(userActivity));
        when(userActivityRepository.existsByUserAndActivityAndStatusAndIdIsNot(userActivity.getUser(),
                userActivity.getActivity(),
                UserActivityStatus.PENDING_ASSIGN,
                ID))
                .thenReturn(true);

        assertThatExceptionOfType(ActiveUserActivityException.class)
                .isThrownBy(() -> userActivityService.update(userActivityDto))
                .withMessage("Request for this user and activity is already active!");
        verify(userActivityRepository, times(1))
                .existsByUserAndActivityAndStatusAndIdIsNot(userActivity.getUser(),
                        userActivity.getActivity(),
                        UserActivityStatus.PENDING_ASSIGN,
                        ID);
    }

    @Test
    void givenUserActivityDtoInvalidStatus_whenUpdate_thenThrowActivityAlreadyExistsException() {
        UserActivityDto userActivityDto = createUserActivityDto();
        userActivityDto.setId(ID);
        UserActivity userActivity = createUserActivity();
        userActivity.setStatus(UserActivityStatus.ABORTED);

        when(userActivityRepository.findById(ID)).thenReturn(Optional.of(userActivity));
        when(userActivityRepository.existsByUserAndActivityAndStatusAndIdIsNot(userActivity.getUser(),
                userActivity.getActivity(),
                UserActivityStatus.PENDING_ASSIGN,
                userActivity.getId()))
                .thenReturn(false);
        when(userActivityRepository.existsByUserAndActivityAndStatusAndIdIsNot(userActivity.getUser(),
                userActivity.getActivity(),
                UserActivityStatus.ASSIGNED,
                userActivity.getId()))
                .thenReturn(false);
        when(userActivityRepository.existsByUserAndActivityAndStatusAndIdIsNot(userActivity.getUser(),
                userActivity.getActivity(),
                UserActivityStatus.PENDING_ABORT,
                userActivity.getId()))
                .thenReturn(false);
        when(userActivityRepository.existsByUserAndActivityAndStatusAndIdIsNot(userActivity.getUser(),
                userActivity.getActivity(),
                UserActivityStatus.IN_PROGRESS,
                userActivity.getId()))
                .thenReturn(false);

        assertThatExceptionOfType(UserActivityStatusException.class)
                .isThrownBy(() -> userActivityService.update(userActivityDto))
                .withMessage("Wrong status for userActivity!");
        verify(userActivityRepository, times(1))
                .existsByUserAndActivityAndStatusAndIdIsNot(userActivity.getUser(),
                        userActivity.getActivity(),
                        UserActivityStatus.PENDING_ASSIGN,
                        userActivity.getId());
        verify(userActivityRepository, times(1))
                .existsByUserAndActivityAndStatusAndIdIsNot(userActivity.getUser(),
                        userActivity.getActivity(),
                        UserActivityStatus.ASSIGNED,
                        userActivity.getId());
        verify(userActivityRepository, times(1))
                .existsByUserAndActivityAndStatusAndIdIsNot(userActivity.getUser(),
                        userActivity.getActivity(),
                        UserActivityStatus.PENDING_ABORT,
                        userActivity.getId());
        verify(userActivityRepository, times(1))
                .existsByUserAndActivityAndStatusAndIdIsNot(userActivity.getUser(),
                        userActivity.getActivity(),
                        UserActivityStatus.IN_PROGRESS,
                        userActivity.getId());
        verify(userActivityRepository, times(0)).save(any());
    }

    @Test
    void givenValidUserActivityDto_whenUpdate_thenReturnUserActivityDto() {
        UserActivityDto userActivityDto = createUserActivityDto();
        userActivityDto.setId(ID);
        userActivityDto.setStatus(UserActivityStatus.ASSIGNED.name());
        UserActivity userActivity = createUserActivity();
        UserActivity userActivityToBeSaved = createUserActivity();
        userActivityToBeSaved.setStatus(UserActivityStatus.ASSIGNED);

        when(userActivityRepository.findById(ID)).thenReturn(Optional.of(userActivity));
        when(userActivityRepository.existsByUserAndActivityAndStatusAndIdIsNot(userActivity.getUser(),
                userActivity.getActivity(),
                UserActivityStatus.PENDING_ASSIGN,
                userActivity.getId()))
                .thenReturn(false);
        when(userActivityRepository.existsByUserAndActivityAndStatusAndIdIsNot(userActivity.getUser(),
                userActivity.getActivity(),
                UserActivityStatus.ASSIGNED,
                userActivity.getId()))
                .thenReturn(false);
        when(userActivityRepository.existsByUserAndActivityAndStatusAndIdIsNot(userActivity.getUser(),
                userActivity.getActivity(),
                UserActivityStatus.PENDING_ABORT,
                userActivity.getId()))
                .thenReturn(false);
        when(userActivityRepository.existsByUserAndActivityAndStatusAndIdIsNot(userActivity.getUser(),
                userActivity.getActivity(),
                UserActivityStatus.IN_PROGRESS,
                userActivity.getId()))
                .thenReturn(false);
        when(userActivityRepository.save(userActivityToBeSaved)).thenReturn(userActivityToBeSaved);

        UserActivityDto updatedUserActivityDto = userActivityService.update(userActivityDto);

        assertThat(updatedUserActivityDto, is(userActivityDto));
        verify(userActivityRepository, times(1)).save(userActivityToBeSaved);
    }

    @Test
    void givenInvalidUserActivityId_whenDelete_thenThrowUserActivityNotFoundException() {
        when(userActivityRepository.existsById(ID)).thenReturn(false);

        assertThatExceptionOfType(UserActivityNotFoundException.class)
                .isThrownBy(() -> userActivityService.deleteById(ID))
                .withMessage("UserActivity was not found!");

        verify(userActivityRepository, times(1)).existsById(ID);
        verify(userActivityRepository, times(0)).deleteById(ID);
    }

    @Test
    void givenValidUserActivityId_whenDelete_thenRepositoryMethodCall() {
        doNothing().when(userActivityRepository).deleteById(ID);
        when(userActivityRepository.existsById(ID)).thenReturn(true);

        userActivityService.deleteById(ID);

        verify(userActivityRepository, times(1)).deleteById(ID);
    }

}
