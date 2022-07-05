package com.epam.spring.homework4.controller.dto;

import com.epam.spring.homework4.service.model.enums.Role;
import com.epam.spring.homework4.service.model.enums.UserStatus;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDto {

    int id;
    String username;
    Role role;
    String email;
    UserStatus status;
    String password;
    String repeatPassword;

}
