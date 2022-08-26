package com.epam.spring.homework6.controller.api;

import com.epam.spring.homework6.controller.dto.UserDto;
import com.epam.spring.homework6.controller.dto.validation.group.OnCreate;
import com.epam.spring.homework6.controller.dto.validation.group.OnUpdate;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;

@Api(tags = "User management API")
@RequestMapping("api/v1/user")
@Validated
public interface UserApi {

    @ApiImplicitParams({
            @ApiImplicitParam(name = "page",
                    paramType = "query",
                    value = "Page number to be read"),
            @ApiImplicitParam(name = "size",
                    paramType = "query",
                    value = "Page size to be read"
            ),
            @ApiImplicitParam(name = "sortBy",
                    paramType = "query",
                    value = "Field that will be used for sorting"),
            @ApiImplicitParam(name = "order",
                    paramType = "query",
                    value = "Order of sorting")}
    )
    @ApiOperation("Get page of sorted users")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    Page<UserDto> getSortedPagedUsers(@RequestParam(defaultValue = "0")
                                      @Min(value = 0, message = "{userApi.getSortedPagedUsers.page.min}")
                                              int page,
                                      @RequestParam(defaultValue = "5")
                                      @Min(value = 0, message = "{userApi.getSortedPagedUsers.size.min}")
                                              int size,
                                      @RequestParam(defaultValue = "id")
                                      @Pattern(regexp = "id|username|role|status",
                                              message = "{userApi.getSortedPagedUsers.sortBy.pattern}")
                                              String sortBy,
                                      @RequestParam(defaultValue = "asc")
                                      @Pattern(regexp = "asc|desc",
                                              message = "{userApi.getSortedPagedUsers.order.pattern}")
                                              String order);

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
