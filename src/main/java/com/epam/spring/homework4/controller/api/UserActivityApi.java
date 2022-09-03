package com.epam.spring.homework4.controller.api;

import com.epam.spring.homework4.controller.dto.UserActivityDto;
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

@Api(tags = "UserActivity management API")
@RequestMapping("api/v1/user-activity")
public interface UserActivityApi {

    @ApiOperation("Get all userActivities")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    List<UserActivityDto> getAllUserActivities();

    @ApiImplicitParams(
            @ApiImplicitParam(name = "body",
                    paramType = "body",
                    required = true,
                    value = "Request activity for user")
    )
    @ApiOperation("Create activity request")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    UserActivityDto requestActivity(@RequestBody @Validated(OnCreate.class)
                                               UserActivityDto userActivity);

    @ApiImplicitParams(
            @ApiImplicitParam(name = "body",
                    paramType = "body",
                    required = true,
                    value = "Update userActivity")
    )
    @ApiOperation("Update userActivity")
    @ResponseStatus(HttpStatus.OK)
    @PutMapping
    UserActivityDto updateUserActivity(@RequestBody @Validated(OnUpdate.class)
                                                     UserActivityDto userActivity);

    @ApiImplicitParams(
            @ApiImplicitParam(name = "userActivityId",
                    paramType = "path",
                    required = true,
                    value = "Id of userActivity to be deleted")
    )
    @ApiOperation("Delete userActivity")
    @DeleteMapping("/{userActivityId}")
    ResponseEntity<Void> deleteUserActivity(@PathVariable int userActivityId);

}
