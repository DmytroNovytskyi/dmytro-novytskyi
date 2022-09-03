package com.epam.spring.homework6.util;

import com.epam.spring.homework6.controller.dto.ActivityDto;
import com.epam.spring.homework6.controller.dto.UserDto;
import com.epam.spring.homework6.controller.dto.UserActivityDto;
import com.epam.spring.homework6.service.model.Activity;
import com.epam.spring.homework6.service.model.User;
import com.epam.spring.homework6.service.model.UserActivity;
import com.epam.spring.homework6.service.model.enums.UserActivityStatus;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class UserActivityTestData {

    public static final Integer ID = 1;

    public static List<UserActivityDto> createUserActivityDtoList() {
        List<UserActivityDto> userActivityDtoList = new ArrayList<>();
        UserDto userDto = UserTestData.createUserDto();
        List<ActivityDto> activityDtoList = ActivityTestData.createActivityDtoList();
        UserActivityDto firstUserActivityDto = new UserActivityDto();
        firstUserActivityDto.setId(1);
        firstUserActivityDto.setUser(userDto);
        firstUserActivityDto.setStatus(UserActivityStatus.PENDING_ASSIGN.name());
        firstUserActivityDto.setCreationDate(new Timestamp(LocalDateTime.now().getNano() / 1000));
        UserActivityDto secondUserActivityDto = new UserActivityDto();
        secondUserActivityDto.setId(2);
        secondUserActivityDto.setUser(userDto);
        secondUserActivityDto.setStatus(UserActivityStatus.PENDING_ABORT.name());
        secondUserActivityDto.setCreationDate(new Timestamp(LocalDateTime.now().getNano() / 1000));
        userActivityDtoList.add(firstUserActivityDto);
        userActivityDtoList.add(secondUserActivityDto);
        userActivityDtoList.get(0).setActivity(activityDtoList.get(0));
        userActivityDtoList.get(1).setActivity(activityDtoList.get(activityDtoList.size() - 1));
        return userActivityDtoList;
    }

    public static List<UserActivity> createUserActivityList() {
        List<UserActivity> userActivities = new ArrayList<>();
        User user = UserTestData.createUser();
        List<Activity> activities = ActivityTestData.createActivityList();
        userActivities.add(UserActivity.builder()
                .id(1)
                .user(user)
                .status(UserActivityStatus.PENDING_ASSIGN)
                .creationDate(new Timestamp(LocalDateTime.now().getNano() / 1000))
                .build());
        userActivities.add(UserActivity.builder()
                .id(2)
                .user(user)
                .status(UserActivityStatus.PENDING_ABORT)
                .creationDate(new Timestamp(LocalDateTime.now().getNano() / 1000))
                .build());
        userActivities.get(0).setActivity(activities.get(0));
        userActivities.get(1).setActivity(activities.get(activities.size() - 1));
        return userActivities;
    }

    public static UserActivity createUserActivity() {
        return UserActivity.builder()
                .id(ID)
                .user(UserTestData.createUser())
                .activity(ActivityTestData.createActivity())
                .status(UserActivityStatus.PENDING_ASSIGN)
                .build();
    }

    public static UserActivityDto createUserActivityDto() {
        UserActivityDto userActivityDto = new UserActivityDto();
        UserDto userDto = UserTestData.createUserDto();
        userDto.setId(1);
        userActivityDto.setUser(userDto);
        ActivityDto activityDto = ActivityTestData.createActivityDto();
        activityDto.setId(1);
        userActivityDto.setActivity(activityDto);
        userActivityDto.setStatus(UserActivityStatus.PENDING_ASSIGN.name());
        return userActivityDto;
    }

}
