package com.epam.spring.homework4.service.model;

import com.epam.spring.homework4.service.model.enums.Role;
import com.epam.spring.homework4.service.model.enums.UserStatus;
import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class User {

    private int id;
    private String username;
    private Role role;
    private String email;
    private String password;
    private UserStatus status;

}
