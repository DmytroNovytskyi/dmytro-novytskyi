package com.epam.spring.homework6.service.mapper;

import com.epam.spring.homework6.controller.dto.UserDto;
import com.epam.spring.homework6.service.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

@Mapper(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mapping(target = "password", ignore = true)
    UserDto mapUserDto(User user);

    @Mapping(target = "id", defaultValue = "0")
    User mapUser(UserDto userDto);

    void mapPresentFields(@MappingTarget User toUser, User fromUser);

}
