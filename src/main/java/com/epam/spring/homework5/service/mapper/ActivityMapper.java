package com.epam.spring.homework5.service.mapper;

import com.epam.spring.homework5.controller.dto.ActivityDto;
import com.epam.spring.homework5.service.model.Activity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

@Mapper(uses = CategoryMapper.class,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ActivityMapper {

    ActivityMapper INSTANCE = Mappers.getMapper(ActivityMapper.class);

    @Mapping(target = "userCount",
            expression = "java(activity.getUserHasActivities() == null ? 0 : activity.getUserHasActivities().size())")
    ActivityDto mapActivityDto(Activity activity);

    @Mapping(target = "id", defaultValue = "0")
    Activity mapActivity(ActivityDto activityDto);

    void mapPresentFields(@MappingTarget Activity toActivity, Activity fromActivity);

}
