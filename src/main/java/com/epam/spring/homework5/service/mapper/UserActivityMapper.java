package com.epam.spring.homework5.service.mapper;

import com.epam.spring.homework5.controller.dto.UserActivityDto;
import com.epam.spring.homework5.service.model.UserActivity;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.sql.Timestamp;
import java.time.Duration;

@Mapper(uses = {UserMapper.class, ActivityMapper.class},
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UserActivityMapper {

    UserActivityMapper INSTANCE = Mappers.getMapper(UserActivityMapper.class);

    UserActivityDto mapUserActivityDto(UserActivity userActivity);

    @Mapping(target = "id", defaultValue = "0")
    UserActivity mapUserActivity(UserActivityDto userActivityDto);

    void mapPresentFields(@MappingTarget UserActivity toUserActivity,
                          UserActivity fromUserActivity);

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
