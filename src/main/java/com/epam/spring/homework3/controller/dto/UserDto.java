package com.epam.spring.homework3.controller.dto;

import com.epam.spring.homework3.service.model.enums.Role;
import com.epam.spring.homework3.service.model.enums.UserStatus;
import lombok.Data;

@Data
public class UserDto {

    private int id;
    private String username;
    private Role role;
    private String email;
    private UserStatus status;
    private String password;
    private String repeatPassword;

}
