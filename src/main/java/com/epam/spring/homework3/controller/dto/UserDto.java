package com.epam.spring.homework3.controller.dto;

import com.epam.spring.homework3.service.model.enums.Role;
import com.epam.spring.homework3.service.model.enums.UserStatus;
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
