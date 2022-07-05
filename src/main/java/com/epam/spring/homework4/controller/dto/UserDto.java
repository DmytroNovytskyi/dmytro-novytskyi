package com.epam.spring.homework4.controller.dto;

import com.epam.spring.homework4.controller.dto.validation.EnumValidator;
import com.epam.spring.homework4.controller.dto.validation.group.OnCreate;
import com.epam.spring.homework4.controller.dto.validation.group.OnUpdate;
import com.epam.spring.homework4.service.model.enums.Role;
import com.epam.spring.homework4.service.model.enums.UserStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Pattern;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDto {

    int id;

    @Pattern(message = "username is not valid(^(?=[a-zA-Z0-9._]{8,45}$)(?!.*[_.]{2})[^_.].*[^_.]$)",
            regexp = "^(?=[a-zA-Z0-9._]{8,45}$)(?!.*[_.]{2})[^_.].*[^_.]$")
    @NotNull(message = "username should not be null", groups = OnCreate.class)
    @Null(message = "username should be null", groups = OnUpdate.class)
    String username;

    @NotNull(message = "role should not be null", groups = OnCreate.class)
    @Null(message = "role should be null", groups = OnUpdate.class)
    Role role;

    @Pattern(message = "email is not valid(^(?=[a-zA-Z0-9._@%-]{6,255}$)[A-Z0-9a-z._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,64}$)",
            regexp = "^(?=[a-zA-Z0-9._@%-]{6,255}$)[A-Z0-9a-z._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,64}$")
    @NotNull(message = "email should not be null", groups = OnCreate.class)
    String email;

    @EnumValidator(name = "status", enumClass = UserStatus.class)
    @NotNull(message = "status should not be null", groups = OnCreate.class)
    String status;

    @Pattern(message = "password is not valid(^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,32}$)",
            regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,32}$")
    @NotNull(message = "password should not be null", groups = OnCreate.class)
    String password;

    @Pattern(message = "password is not valid(^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,32}$)",
            regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,32}$")
    @NotNull(message = "password should not be null", groups = OnCreate.class)
    String repeatPassword;

}
