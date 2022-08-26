package com.epam.spring.homework6.service.impl;

import com.epam.spring.homework6.controller.dto.UserHasActivityDto;
import com.epam.spring.homework6.service.exception.ActiveUserHasActivityException;
import com.epam.spring.homework6.service.exception.UserHasActivityNotFoundException;
import com.epam.spring.homework6.service.exception.UserHasActivityStatusException;
import com.epam.spring.homework6.service.mapper.UserHasActivityMapper;
import com.epam.spring.homework6.service.model.UserHasActivity;
import com.epam.spring.homework6.service.model.enums.UserHasActivityStatus;
import com.epam.spring.homework6.service.repository.UserHasActivityRepository;
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
import static com.epam.spring.homework6.util.UserHasActivityTestData.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserHasActivityServiceImplTest {

    @InjectMocks
    UserHasActivityServiceImpl userHasActivityService;

    @Mock
    UserHasActivityRepository userHasActivityRepository;

    @Test
    void givenPageableData_whenFindAll_thenReturnPageOfUserHasActivities() {
        Pageable pageable = createPageable();
        List<UserHasActivity> userHasActivities = createUserHasActivityList();
        Page<UserHasActivity> userHasActivityPage =
                new PageImpl<>(userHasActivities, pageable, userHasActivities.size());
        Page<UserHasActivityDto> expectedPage = userHasActivityPage
                .map(UserHasActivityMapper.INSTANCE::mapUserHasActivityDto);

        when(userHasActivityRepository.findAll(pageable)).thenReturn(userHasActivityPage);

        Page<UserHasActivityDto> actualPage = userHasActivityService.getAll(PAGE, SIZE, SORT_BY, ORDER);

        assertThat(actualPage.getSize(), is(SIZE));
        assertThat(actualPage.getPageable(), is(pageable));
        assertThat(actualPage, is(expectedPage));
        verify(userHasActivityRepository, times(1)).findAll(pageable);
    }

    @Test
    void givenUserHasActivityDtoWithActiveCombination_whenCreate_thenThrowActiveUserHasActivityException() {
        UserHasActivityDto userHasActivityDto = createUserHasActivityDto();
        UserHasActivity userHasActivity = createUserHasActivity();

        when(userHasActivityRepository.existsByUserAndActivityAndStatus(userHasActivity.getUser(),
                userHasActivity.getActivity(),
                UserHasActivityStatus.PENDING_ASSIGN))
                .thenReturn(true);

        assertThatExceptionOfType(ActiveUserHasActivityException.class)
                .isThrownBy(() -> userHasActivityService.request(userHasActivityDto))
                .withMessage("Request for this user and activity is already active!");
        verify(userHasActivityRepository, times(1))
                .existsByUserAndActivityAndStatus(userHasActivity.getUser(),
                        userHasActivity.getActivity(),
                        UserHasActivityStatus.PENDING_ASSIGN);
    }

    @Test
    void givenValidUserHasActivityDto_whenCreate_thenReturnCreatedUserHasActivityDto() {
        UserHasActivityDto userHasActivityDto = createUserHasActivityDto();
        UserHasActivity userHasActivity = createUserHasActivity();

        when(userHasActivityRepository
                .save(UserHasActivityMapper.INSTANCE.mapUserHasActivity(userHasActivityDto)))
                .thenReturn(userHasActivity);

        UserHasActivityDto actualUserHasActivityDto = userHasActivityService.request(userHasActivityDto);
        UserHasActivityDto expectedUserHasActivityDto = createUserHasActivityDto();
        expectedUserHasActivityDto.setId(userHasActivity.getId());

        assertThat(actualUserHasActivityDto, is(expectedUserHasActivityDto));
        verify(userHasActivityRepository, times(1))
                .save(UserHasActivityMapper.INSTANCE.mapUserHasActivity(userHasActivityDto));
    }

    @Test
    void givenUserHasActivityDtoWithInvalidId_whenUpdate_thenThrowUserHasActivityNotFoundException() {
        UserHasActivityDto userHasActivityDto = createUserHasActivityDto();
        userHasActivityDto.setId(ID);

        when(userHasActivityRepository.findById(ID)).thenReturn(Optional.empty());

        assertThatExceptionOfType(UserHasActivityNotFoundException.class)
                .isThrownBy(() -> userHasActivityService.update(userHasActivityDto))
                .withMessage("UserHasActivity was not found!");
        verify(userHasActivityRepository, times(1)).findById(ID);
        verify(userHasActivityRepository, times(0)).save(any());
    }

    @Test
    void givenUserHasActivityDtoWithActiveCombination_whenUpdate_thenThrowActiveUserHasActivityException() {
        UserHasActivityDto userHasActivityDto = createUserHasActivityDto();
        userHasActivityDto.setId(ID);
        UserHasActivity userHasActivity = createUserHasActivity();

        when(userHasActivityRepository.findById(ID)).thenReturn(Optional.of(userHasActivity));
        when(userHasActivityRepository.existsByUserAndActivityAndStatusAndIdIsNot(userHasActivity.getUser(),
                userHasActivity.getActivity(),
                UserHasActivityStatus.PENDING_ASSIGN,
                ID))
                .thenReturn(true);

        assertThatExceptionOfType(ActiveUserHasActivityException.class)
                .isThrownBy(() -> userHasActivityService.update(userHasActivityDto))
                .withMessage("Request for this user and activity is already active!");
        verify(userHasActivityRepository, times(1))
                .existsByUserAndActivityAndStatusAndIdIsNot(userHasActivity.getUser(),
                        userHasActivity.getActivity(),
                        UserHasActivityStatus.PENDING_ASSIGN,
                        ID);
    }

    @Test
    void givenUserHasActivityDtoInvalidStatus_whenUpdate_thenThrowActivityAlreadyExistsException() {
        UserHasActivityDto userHasActivityDto = createUserHasActivityDto();
        userHasActivityDto.setId(ID);
        UserHasActivity userHasActivity = createUserHasActivity();
        userHasActivity.setStatus(UserHasActivityStatus.ABORTED);

        when(userHasActivityRepository.findById(ID)).thenReturn(Optional.of(userHasActivity));
        when(userHasActivityRepository.existsByUserAndActivityAndStatusAndIdIsNot(userHasActivity.getUser(),
                userHasActivity.getActivity(),
                UserHasActivityStatus.PENDING_ASSIGN,
                userHasActivity.getId()))
                .thenReturn(false);
        when(userHasActivityRepository.existsByUserAndActivityAndStatusAndIdIsNot(userHasActivity.getUser(),
                userHasActivity.getActivity(),
                UserHasActivityStatus.ASSIGNED,
                userHasActivity.getId()))
                .thenReturn(false);
        when(userHasActivityRepository.existsByUserAndActivityAndStatusAndIdIsNot(userHasActivity.getUser(),
                userHasActivity.getActivity(),
                UserHasActivityStatus.PENDING_ABORT,
                userHasActivity.getId()))
                .thenReturn(false);
        when(userHasActivityRepository.existsByUserAndActivityAndStatusAndIdIsNot(userHasActivity.getUser(),
                userHasActivity.getActivity(),
                UserHasActivityStatus.IN_PROGRESS,
                userHasActivity.getId()))
                .thenReturn(false);

        assertThatExceptionOfType(UserHasActivityStatusException.class)
                .isThrownBy(() -> userHasActivityService.update(userHasActivityDto))
                .withMessage("Wrong status for userHasActivity!");
        verify(userHasActivityRepository, times(1))
                .existsByUserAndActivityAndStatusAndIdIsNot(userHasActivity.getUser(),
                        userHasActivity.getActivity(),
                        UserHasActivityStatus.PENDING_ASSIGN,
                        userHasActivity.getId());
        verify(userHasActivityRepository, times(1))
                .existsByUserAndActivityAndStatusAndIdIsNot(userHasActivity.getUser(),
                        userHasActivity.getActivity(),
                        UserHasActivityStatus.ASSIGNED,
                        userHasActivity.getId());
        verify(userHasActivityRepository, times(1))
                .existsByUserAndActivityAndStatusAndIdIsNot(userHasActivity.getUser(),
                        userHasActivity.getActivity(),
                        UserHasActivityStatus.PENDING_ABORT,
                        userHasActivity.getId());
        verify(userHasActivityRepository, times(1))
                .existsByUserAndActivityAndStatusAndIdIsNot(userHasActivity.getUser(),
                        userHasActivity.getActivity(),
                        UserHasActivityStatus.IN_PROGRESS,
                        userHasActivity.getId());
        verify(userHasActivityRepository, times(0)).save(any());
    }

    @Test
    void givenValidUserHasActivityDto_whenUpdate_thenReturnUserHasActivityDto() {
        UserHasActivityDto userHasActivityDto = createUserHasActivityDto();
        userHasActivityDto.setId(ID);
        userHasActivityDto.setStatus(UserHasActivityStatus.ASSIGNED.name());
        UserHasActivity userHasActivity = createUserHasActivity();
        UserHasActivity userHasActivityToBeSaved = createUserHasActivity();
        userHasActivityToBeSaved.setStatus(UserHasActivityStatus.ASSIGNED);

        when(userHasActivityRepository.findById(ID)).thenReturn(Optional.of(userHasActivity));
        when(userHasActivityRepository.existsByUserAndActivityAndStatusAndIdIsNot(userHasActivity.getUser(),
                userHasActivity.getActivity(),
                UserHasActivityStatus.PENDING_ASSIGN,
                userHasActivity.getId()))
                .thenReturn(false);
        when(userHasActivityRepository.existsByUserAndActivityAndStatusAndIdIsNot(userHasActivity.getUser(),
                userHasActivity.getActivity(),
                UserHasActivityStatus.ASSIGNED,
                userHasActivity.getId()))
                .thenReturn(false);
        when(userHasActivityRepository.existsByUserAndActivityAndStatusAndIdIsNot(userHasActivity.getUser(),
                userHasActivity.getActivity(),
                UserHasActivityStatus.PENDING_ABORT,
                userHasActivity.getId()))
                .thenReturn(false);
        when(userHasActivityRepository.existsByUserAndActivityAndStatusAndIdIsNot(userHasActivity.getUser(),
                userHasActivity.getActivity(),
                UserHasActivityStatus.IN_PROGRESS,
                userHasActivity.getId()))
                .thenReturn(false);
        when(userHasActivityRepository.save(userHasActivityToBeSaved)).thenReturn(userHasActivityToBeSaved);

        UserHasActivityDto updatedUserHasActivityDto = userHasActivityService.update(userHasActivityDto);

        assertThat(updatedUserHasActivityDto, is(userHasActivityDto));
        verify(userHasActivityRepository, times(1)).save(userHasActivityToBeSaved);
    }

    @Test
    void givenInvalidUserHasActivityId_whenDelete_thenThrowUserHasActivityNotFoundException() {
        when(userHasActivityRepository.existsById(ID)).thenReturn(false);

        assertThatExceptionOfType(UserHasActivityNotFoundException.class)
                .isThrownBy(() -> userHasActivityService.deleteById(ID))
                .withMessage("UserHasActivity was not found!");

        verify(userHasActivityRepository, times(1)).existsById(ID);
        verify(userHasActivityRepository, times(0)).deleteById(ID);
    }

    @Test
    void givenValidUserHasActivityId_whenDelete_thenRepositoryMethodCall() {
        doNothing().when(userHasActivityRepository).deleteById(ID);
        when(userHasActivityRepository.existsById(ID)).thenReturn(true);

        userHasActivityService.deleteById(ID);

        verify(userHasActivityRepository, times(1)).deleteById(ID);
    }

}
