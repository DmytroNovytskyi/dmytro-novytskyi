package com.epam.spring.timekeeper.service.mapper;

import com.epam.spring.timekeeper.controller.dto.UserHasActivityDto;
import com.epam.spring.timekeeper.service.model.UserHasActivity;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import java.sql.Timestamp;
import java.time.Duration;

@Mapper(uses = {UserMapper.class, ActivityMapper.class})
public interface UserHasActivityMapper {

    UserHasActivityMapper INSTANCE = Mappers.getMapper(UserHasActivityMapper.class);

    UserHasActivityDto mapUserHasActivityDto(UserHasActivity userHasActivity);

    UserHasActivity mapUserHasActivity(UserHasActivityDto userHasActivityDto);

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
