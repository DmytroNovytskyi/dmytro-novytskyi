package com.epam.spring.homework6.util;

import com.epam.spring.homework6.controller.dto.ActivityDto;
import com.epam.spring.homework6.controller.dto.CategoryDto;
import com.epam.spring.homework6.service.model.Activity;
import com.epam.spring.homework6.service.model.Category;
import com.epam.spring.homework6.service.model.enums.ActivityStatus;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class ActivityTestData {

    public static final Integer ID = 1;
    public static final String NAME = "activity";
    public static final String DESCRIPTION = "description";

    public static List<ActivityDto> createActivityDtoList() {
        List<ActivityDto> activityDtoList = new ArrayList<>();
        CategoryDto categoryDto = CategoryTestData.createCategoryDto();
        ActivityDto firstActivityDto = new ActivityDto();
        firstActivityDto.setId(1);
        firstActivityDto.setCategory(categoryDto);
        firstActivityDto.setName("activity1");
        firstActivityDto.setStatus(ActivityStatus.OPENED.name());
        firstActivityDto.setDescription("activity1 description");
        firstActivityDto.setUserCount(0);
        ActivityDto secondActivityDto = new ActivityDto();
        secondActivityDto.setId(1);
        secondActivityDto.setCategory(categoryDto);
        secondActivityDto.setName("activity2");
        secondActivityDto.setStatus(ActivityStatus.OPENED.name());
        secondActivityDto.setDescription("activity2 description");
        secondActivityDto.setUserCount(0);
        activityDtoList.add(firstActivityDto);
        activityDtoList.add(secondActivityDto);
        return activityDtoList;
    }

    public static List<Activity> createActivityList() {
        List<Activity> activities = new ArrayList<>();
        Category category = CategoryTestData.createCategory();
        activities.add(Activity.builder()
                .id(1)
                .category(category)
                .name("activity1")
                .status(ActivityStatus.OPENED)
                .description("activity1 description")
                .build());
        activities.add(Activity.builder()
                .id(2)
                .category(category)
                .name("activity2")
                .status(ActivityStatus.OPENED)
                .description("activity2 description")
                .build());
        return activities;
    }

    public static ActivityDto createActivityDto() {
        ActivityDto activityDto = new ActivityDto();
        activityDto.setName(NAME);
        activityDto.setStatus(ActivityStatus.OPENED.name());
        activityDto.setCategory(CategoryTestData.createCategoryDto());
        activityDto.setDescription(DESCRIPTION);
        activityDto.setUserCount(0);
        return activityDto;
    }

    public static Activity createActivity() {
        return Activity.builder()
                .id(ID)
                .name(NAME)
                .status(ActivityStatus.OPENED)
                .category(CategoryTestData.createCategory())
                .description(DESCRIPTION)
                .userActivities(new HashSet<>())
                .build();
    }

}
