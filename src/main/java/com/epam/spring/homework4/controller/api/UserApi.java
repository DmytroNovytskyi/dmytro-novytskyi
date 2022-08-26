package com.epam.spring.homework4.controller.api;

import com.epam.spring.homework4.controller.dto.UserDto;
import com.epam.spring.homework4.controller.dto.validation.group.OnCreate;
import com.epam.spring.homework4.controller.dto.validation.group.OnUpdate;
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
@RequestMapping("api/v1/user")
public interface UserApi {

    @ApiOperation("Get all users")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    List<UserDto> getAllUsers();

    @ApiImplicitParams(
            @ApiImplicitParam(name = "body",
                    paramType = "body",
                    required = true,
                    value = "User to be created")
    )
    @ApiOperation("Create user")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    UserDto createUser(@RequestBody @Validated(OnCreate.class) UserDto user);

    @ApiImplicitParams(
            @ApiImplicitParam(name = "body",
                    paramType = "body",
                    required = true,
                    value = "User to be updated")
    )
    @ApiOperation("Update user")
    @ResponseStatus(HttpStatus.OK)
    @PutMapping
    UserDto updateUser(@RequestBody @Validated(OnUpdate.class) UserDto user);

    @ApiImplicitParams(
            @ApiImplicitParam(name = "userId",
                    paramType = "path",
                    required = true,
                    value = "Id of user to be deleted")
    )
    @ApiOperation("Delete user")
    @DeleteMapping("/{userId}")
    ResponseEntity<Void> deleteUser(@PathVariable int userId);

}
