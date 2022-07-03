package com.epam.spring.timekeeper.service.mapper;

import com.epam.spring.timekeeper.controller.dto.ActivityDto;
import com.epam.spring.timekeeper.service.model.Activity;
import com.epam.spring.timekeeper.service.model.UserHasActivity;
import org.mapstruct.AfterMapping;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(uses = CategoryMapper.class)
public interface ActivityMapper {

    ActivityMapper INSTANCE = Mappers.getMapper(ActivityMapper.class);

    ActivityDto mapActivityDto(Activity activity, @Context List<UserHasActivity> userHasActivities);

    Activity mapActivity(ActivityDto activityDto);

    @AfterMapping
    default void calculateUserCount(@Context List<UserHasActivity> userHasActivities, @MappingTarget ActivityDto activityDto) {
        activityDto.setUserCount((int) userHasActivities.stream()
                .filter(u -> u.getActivity().getId() == activityDto.getId())
                .count());
    }

}
