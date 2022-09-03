package com.epam.spring.homework4.service.mapper;

import com.epam.spring.homework4.controller.dto.UserActivityDto;
import com.epam.spring.homework4.service.model.UserActivity;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

import java.sql.Timestamp;
import java.time.Duration;

@Mapper(uses = {UserMapper.class, ActivityMapper.class},
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UserActivityMapper {

    UserActivityMapper INSTANCE = Mappers.getMapper(UserActivityMapper.class);

    UserActivityDto mapUserActivityDto(UserActivity userActivity);

    UserActivity mapUserActivity(UserActivityDto userActivityDto);

    void mapPresentFields(@MappingTarget UserActivity userActivity,
                          UserActivityDto userActivityDto);

    @AfterMapping
    default void calculateAndSetTimeSpent(@MappingTarget UserActivityDto userActivityDto) {
        Timestamp start = userActivityDto.getStartTime();
        Timestamp end = userActivityDto.getEndTime();
        if (start != null && end != null) {
            Duration duration = Duration.ofMillis(end.getTime() - start.getTime());
            userActivityDto.setTimeSpent(String.format("%02d:%02d:%02d",
                    duration.toHours(), duration.toMinutesPart(), duration.toSecondsPart()));
        }
    }

}
