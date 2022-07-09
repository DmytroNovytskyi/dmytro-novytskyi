package com.epam.spring.homework5.service.mapper;

import com.epam.spring.homework5.controller.dto.UserDto;
import com.epam.spring.homework5.service.model.User;
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

    User mapUser(UserDto userDto);

    void mapPresentFields(@MappingTarget User user, UserDto userDto);

}
