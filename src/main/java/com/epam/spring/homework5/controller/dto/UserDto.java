package com.epam.spring.homework5.controller.dto;

import com.epam.spring.homework5.controller.dto.validation.EnumValidator;
import com.epam.spring.homework5.controller.dto.validation.group.OnCreate;
import com.epam.spring.homework5.controller.dto.validation.group.OnUpdate;
import com.epam.spring.homework5.service.model.enums.Role;
import com.epam.spring.homework5.service.model.enums.UserStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Pattern;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDto {

    int id;

    @Pattern(message = "{user.username.pattern}",
            regexp = "^(?=[a-zA-Z0-9._]{8,45}$)(?!.*[_.]{2})[^_.].*[^_.]$")
    @NotNull(message = "{user.username.notNull}", groups = OnCreate.class)
    @Null(message = "{user.username.null}", groups = OnUpdate.class)
    String username;

    @EnumValidator(name = "{user.role.name}", enumClass = Role.class)
    @NotNull(message = "{user.role.notNull}", groups = OnCreate.class)
    @Null(message = "{user.role.null}", groups = OnUpdate.class)
    Role role;

    @Pattern(message = "{user.email.pattern}",
            regexp = "^(?=[a-zA-Z0-9._@%-]{6,255}$)[A-Z0-9a-z._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,64}$")
    @NotNull(message = "{user.email.notNull}", groups = OnCreate.class)
    String email;

    @EnumValidator(name = "{user.status.name}", enumClass = UserStatus.class)
    @NotNull(message = "{user.status.notNull}", groups = OnCreate.class)
    String status;

    @Pattern(message = "{user.password.pattern}",
            regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,32}$")
    @NotNull(message = "{user.password.notNull}", groups = OnCreate.class)
    String password;

    @Pattern(message = "{user.repeatPassword.pattern}",
            regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,32}$")
    @NotNull(message = "{user.repeatPassword.notNull}", groups = OnCreate.class)
    String repeatPassword;

}
