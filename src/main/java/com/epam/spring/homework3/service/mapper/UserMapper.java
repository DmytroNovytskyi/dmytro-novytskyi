package com.epam.spring.homework3.service.mapper;

import com.epam.spring.homework3.controller.dto.UserDto;
import com.epam.spring.homework3.service.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mapping(target = "password", ignore = true)
    UserDto mapUserDto(User user);

    User mapUser(UserDto userDto);

}
