package com.epam.spring.homework6.service.impl;

import com.epam.spring.homework6.controller.dto.ActivityDto;
import com.epam.spring.homework6.service.exception.ActivityAlreadyExistsException;
import com.epam.spring.homework6.service.exception.ActivityNotFoundException;
import com.epam.spring.homework6.service.exception.CategoryNotFoundException;
import com.epam.spring.homework6.service.mapper.ActivityMapper;
import com.epam.spring.homework6.service.model.Activity;
import com.epam.spring.homework6.service.repository.ActivityRepository;
import com.epam.spring.homework6.service.repository.CategoryRepository;
import com.epam.spring.homework6.util.CategoryTestData;
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

import static com.epam.spring.homework6.util.ActivityTestData.*;
import static com.epam.spring.homework6.util.CommonTestData.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ActivityServiceImplTest {

    @InjectMocks
    private ActivityServiceImpl activityService;

    @Mock
    private ActivityRepository activityRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @Test
    void givenPageableData_whenFindAll_thenReturnPageOfActivities() {
        Pageable pageable = createPageable();
        List<Activity> activities = createActivityList();
        Page<Activity> activityPage = new PageImpl<>(activities, pageable, activities.size());
        Page<ActivityDto> expectedPage = activityPage.map(ActivityMapper.INSTANCE::mapActivityDto);

        when(activityRepository.findAll(pageable)).thenReturn(activityPage);

        Page<ActivityDto> actualPage = activityService.getAll(PAGE, SIZE, SORT_BY, ORDER);

        assertThat(actualPage.getSize(), is(SIZE));
        assertThat(actualPage.getPageable(), is(pageable));
        assertThat(actualPage, is(expectedPage));
        verify(activityRepository, times(1)).findAll(pageable);
    }

    @Test
    void givenActivityDtoWithExistingName_whenCreate_thenThrowActivityAlreadyExistsException() {
        ActivityDto activityDto = createActivityDto();

        when(activityRepository.existsByName(activityDto.getName())).thenReturn(true);

        assertThatExceptionOfType(ActivityAlreadyExistsException.class)
                .isThrownBy(() -> activityService.create(activityDto))
                .withMessage("Activity with this name already exists!");
        verify(activityRepository, times(1)).existsByName(NAME);
        verify(activityRepository, times(0)).save(any());
    }

    @Test
    void givenValidActivityDto_whenCreate_thenReturnCreatedActivityDto() {
        ActivityDto activityDto = createActivityDto();
        Activity activity = createActivity();

        when(activityRepository.save(ActivityMapper.INSTANCE.mapActivity(activityDto)))
                .thenReturn(activity);

        ActivityDto actualActivityDto = activityService.create(activityDto);
        ActivityDto expectedActivityDto = createActivityDto();
        expectedActivityDto.setId(activity.getId());

        assertThat(actualActivityDto, is(expectedActivityDto));
        verify(activityRepository, times(1))
                .save(ActivityMapper.INSTANCE.mapActivity(activityDto));
    }

    @Test
    void givenActivityDtoWithInvalidId_whenUpdate_thenThrowActivityNotFoundException() {
        ActivityDto activityDto = createActivityDto();
        activityDto.setId(ID);

        when(activityRepository.findById(ID)).thenReturn(Optional.empty());

        assertThatExceptionOfType(ActivityNotFoundException.class)
                .isThrownBy(() -> activityService.update(activityDto))
                .withMessage("Activity was not found!");
        verify(activityRepository, times(1)).findById(ID);
        verify(activityRepository, times(0)).save(any());
    }

    @Test
    void givenActivityDtoWithDuplicateName_whenUpdate_thenThrowActivityAlreadyExistsException() {
        ActivityDto activityDto = createActivityDto();
        activityDto.setId(ID);
        Activity activity = createActivity();

        when(activityRepository.findById(ID)).thenReturn(Optional.of(activity));
        when(activityRepository.existsByNameAndIdIsNot(NAME, ID)).thenReturn(true);

        assertThatExceptionOfType(ActivityAlreadyExistsException.class)
                .isThrownBy(() -> activityService.update(activityDto))
                .withMessage("Activity with this name already exists!");
        verify(activityRepository, times(1)).existsByNameAndIdIsNot(NAME, ID);
        verify(activityRepository, times(0)).save(any());
    }

    @Test
    void givenActivityDtoWithInvalidCategoryId_whenUpdate_thenThrowCategoryNotFoundException() {
        ActivityDto activityDto = createActivityDto();
        activityDto.setId(ID);
        Activity activity = createActivity();
        activity.getCategory().setId(CategoryTestData.OTHER_ID);

        when(activityRepository.findById(ID)).thenReturn(Optional.of(activity));
        when(activityRepository.existsByNameAndIdIsNot(NAME, ID)).thenReturn(false);
        when(categoryRepository.findById(CategoryTestData.ID)).thenReturn(Optional.empty());

        assertThatExceptionOfType(CategoryNotFoundException.class)
                .isThrownBy(() -> activityService.update(activityDto))
                .withMessage("Category was not found!");
        verify(categoryRepository, times(1)).findById(CategoryTestData.ID);
        verify(activityRepository, times(0)).save(any());
    }

    @Test
    void givenValidActivityDto_whenUpdate_thenReturnUpdatedActivityDto() {
        ActivityDto activityDto = createActivityDto();
        activityDto.setId(ID);
        Activity activity = createActivity();
        activity.getCategory().setId(CategoryTestData.OTHER_ID);

        when(activityRepository.findById(ID)).thenReturn(Optional.of(activity));
        when(activityRepository.existsByNameAndIdIsNot(NAME, ID)).thenReturn(false);
        when(categoryRepository.findById(CategoryTestData.ID)).
                thenReturn(Optional.of(CategoryTestData.createCategory()));
        when(activityRepository.save(createActivity())).thenReturn(createActivity());

        ActivityDto updatedActivityDto = activityService.update(activityDto);

        assertThat(updatedActivityDto, is(activityDto));
        verify(activityRepository, times(1)).save(createActivity());
    }

    @Test
    void givenInvalidActivityId_whenDelete_thenThrowActivityNotFoundException() {
        when(activityRepository.existsById(ID)).thenReturn(false);

        assertThatExceptionOfType(ActivityNotFoundException.class)
                .isThrownBy(() -> activityService.delete(ID))
                .withMessage("Activity was not found!");

        verify(activityRepository, times(1)).existsById(ID);
        verify(activityRepository, times(0)).deleteById(ID);
    }

    @Test
    void givenValidActivityId_whenDelete_thenRepositoryMethodCall() {
        doNothing().when(activityRepository).deleteById(ID);
        when(activityRepository.existsById(ID)).thenReturn(true);

        activityService.delete(ID);

        verify(activityRepository, times(1)).deleteById(ID);
    }

}
