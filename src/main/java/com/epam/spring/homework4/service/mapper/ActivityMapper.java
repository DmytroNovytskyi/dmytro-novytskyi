package com.epam.spring.homework4.service.mapper;

import com.epam.spring.homework4.controller.dto.ActivityDto;
import com.epam.spring.homework4.service.model.Activity;
import com.epam.spring.homework4.service.model.UserHasActivity;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(uses = CategoryMapper.class,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ActivityMapper {

    ActivityMapper INSTANCE = Mappers.getMapper(ActivityMapper.class);

    ActivityDto mapActivityDto(Activity activity, @Context List<UserHasActivity> userHasActivities);

    Activity mapActivity(ActivityDto activityDto);

    void mapPresentFields(@MappingTarget Activity activity, ActivityDto activityDto);

    @AfterMapping
    default void calculateUserCount(@Context List<UserHasActivity> userHasActivities,
                                    @MappingTarget ActivityDto activityDto) {
        activityDto.setUserCount((int) userHasActivities.stream()
                .filter(u -> u.getActivity().getId() == activityDto.getId())
                .count());
    }

}
