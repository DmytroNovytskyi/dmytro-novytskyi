package com.epam.spring.homework6.util;

import com.epam.spring.homework6.controller.dto.ActivityDto;
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
                .userHasActivities(new HashSet<>())
                .build();
    }

}
