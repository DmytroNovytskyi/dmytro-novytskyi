package com.epam.spring.timekeeper.service.model;

import com.epam.spring.timekeeper.service.model.enums.Role;
import com.epam.spring.timekeeper.service.model.enums.UserStatus;
import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class User {

    int id;
    String username;
    Role role;
    String email;
    String password;
    UserStatus status;

}
