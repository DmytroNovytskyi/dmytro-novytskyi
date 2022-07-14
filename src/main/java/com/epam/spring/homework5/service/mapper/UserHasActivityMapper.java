package com.epam.spring.homework5.service.mapper;

import com.epam.spring.homework5.controller.dto.UserHasActivityDto;
import com.epam.spring.homework5.service.model.UserHasActivity;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.sql.Timestamp;
import java.time.Duration;

@Mapper(uses = {UserMapper.class, ActivityMapper.class},
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UserHasActivityMapper {

    UserHasActivityMapper INSTANCE = Mappers.getMapper(UserHasActivityMapper.class);

    UserHasActivityDto mapUserHasActivityDto(UserHasActivity userHasActivity);

    @Mapping(target = "id", defaultValue = "0")
    UserHasActivity mapUserHasActivity(UserHasActivityDto userHasActivityDto);

    void mapPresentFields(@MappingTarget UserHasActivity toUserHasActivity,
                          UserHasActivity fromUserHasActivity);

    @AfterMapping
    default void calculateAndSetTimeSpent(@MappingTarget UserHasActivityDto userHasActivityDto) {
        Timestamp start = userHasActivityDto.getStartTime();
        Timestamp end = userHasActivityDto.getEndTime();
        if (start != null && end != null) {
            Duration duration = Duration.ofMillis(end.getTime() - start.getTime());
            userHasActivityDto.setTimeSpent(String.format("%02d:%02d:%02d",
                    duration.toHours(), duration.toMinutesPart(), duration.toSecondsPart()));
        }
    }

}
