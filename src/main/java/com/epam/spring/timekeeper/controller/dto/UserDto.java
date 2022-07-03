package com.epam.spring.timekeeper.controller.dto;

import com.epam.spring.timekeeper.service.model.enums.Role;
import com.epam.spring.timekeeper.service.model.enums.UserStatus;
import lombok.Data;

@Data
public class UserDto {

    int id;
    String username;
    Role role;
    String email;
    UserStatus status;
    String password;
    String repeatPassword;

}
