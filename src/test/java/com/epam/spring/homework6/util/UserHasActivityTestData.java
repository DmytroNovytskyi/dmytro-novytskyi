package com.epam.spring.homework6.util;

import com.epam.spring.homework6.controller.dto.ActivityDto;
import com.epam.spring.homework6.controller.dto.UserDto;
import com.epam.spring.homework6.controller.dto.UserHasActivityDto;
import com.epam.spring.homework6.service.model.Activity;
import com.epam.spring.homework6.service.model.User;
import com.epam.spring.homework6.service.model.UserHasActivity;
import com.epam.spring.homework6.service.model.enums.UserHasActivityStatus;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class UserHasActivityTestData {

    public static final Integer ID = 1;

    public static List<UserHasActivity> createUserHasActivityList() {
        List<UserHasActivity> userHasActivities = new ArrayList<>();
        User user = UserTestData.createUser();
        List<Activity> activities = ActivityTestData.createActivityList();
        userHasActivities.add(UserHasActivity.builder()
                .id(1)
                .user(user)
                .status(UserHasActivityStatus.PENDING_ASSIGN)
                .creationDate(new Timestamp(LocalDateTime.now().getNano() / 1000))
                .build());
        userHasActivities.add(UserHasActivity.builder()
                .id(2)
                .user(user)
                .status(UserHasActivityStatus.PENDING_ABORT)
                .creationDate(new Timestamp(LocalDateTime.now().getNano() / 1000))
                .build());
        userHasActivities.get(0).setActivity(activities.get(0));
        userHasActivities.get(1).setActivity(activities.get(activities.size() - 1));
        return userHasActivities;
    }

    public static UserHasActivity createUserHasActivity() {
        return UserHasActivity.builder()
                .id(ID)
                .user(UserTestData.createUser())
                .activity(ActivityTestData.createActivity())
                .status(UserHasActivityStatus.PENDING_ASSIGN)
                .build();
    }

    public static UserHasActivityDto createUserHasActivityDto() {
        UserHasActivityDto userHasActivityDto = new UserHasActivityDto();
        UserDto userDto = UserTestData.createUserDto();
        userDto.setId(1);
        userHasActivityDto.setUser(userDto);
        ActivityDto activityDto = ActivityTestData.createActivityDto();
        activityDto.setId(1);
        userHasActivityDto.setActivity(activityDto);
        userHasActivityDto.setStatus(UserHasActivityStatus.PENDING_ASSIGN.name());
        return userHasActivityDto;
    }

}
