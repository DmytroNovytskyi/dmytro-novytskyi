package com.epam.spring.homework5.controller.api;

import com.epam.spring.homework5.controller.dto.UserDto;
import com.epam.spring.homework5.controller.dto.validation.group.OnCreate;
import com.epam.spring.homework5.controller.dto.validation.group.OnUpdate;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "User management API")
@RequestMapping("api/v1")
public interface UserApi {

    @ApiOperation("Get all users")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/user")
    List<UserDto> getAllUsers();

    @ApiImplicitParams(
            @ApiImplicitParam(name = "body",
                    paramType = "body",
                    required = true,
                    value = "User to be created")
    )
    @ApiOperation("Create user")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/user")
    UserDto createUser(@RequestBody @Validated(OnCreate.class) UserDto user);

    @ApiImplicitParams(
            @ApiImplicitParam(name = "body",
                    paramType = "body",
                    required = true,
                    value = "User to be updated")
    )
    @ApiOperation("Update user")
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/user")
    UserDto updateUser(@RequestBody @Validated(OnUpdate.class) UserDto user);

    @ApiImplicitParams(
            @ApiImplicitParam(name = "userId",
                    paramType = "path",
                    required = true,
                    value = "Id of user to be deleted")
    )
    @ApiOperation("Delete user")
    @DeleteMapping("/user/{userId}")
    ResponseEntity<Void> deleteUser(@PathVariable int userId);

}
