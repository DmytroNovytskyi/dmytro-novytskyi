package com.epam.spring.homework5.controller.api;

import com.epam.spring.homework5.controller.dto.UserHasActivityDto;
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

@Api(tags = "UserHasActivity management API")
@RequestMapping("api/v1")
public interface UserHasActivityApi {

    @ApiOperation("Get all userHasActivities")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/user-has-activity")
    List<UserHasActivityDto> getAllUserHasActivities();

    @ApiImplicitParams(
            @ApiImplicitParam(name = "body",
                    paramType = "body",
                    required = true,
                    value = "Request activity for user")
    )
    @ApiOperation("Create activity request")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/user-has-activity")
    UserHasActivityDto requestActivity(@RequestBody @Validated(OnCreate.class)
                                               UserHasActivityDto userHasActivity);

    @ApiImplicitParams(
            @ApiImplicitParam(name = "body",
                    paramType = "body",
                    required = true,
                    value = "Update userHasActivity")
    )
    @ApiOperation("Update userHasActivity")
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/user-has-activity")
    UserHasActivityDto updateUserHasActivity(@RequestBody @Validated(OnUpdate.class)
                                                     UserHasActivityDto userHasActivity);

    @ApiImplicitParams(
            @ApiImplicitParam(name = "userHasActivityId",
                    paramType = "path",
                    required = true,
                    value = "Id of userHasActivity to be deleted")
    )
    @ApiOperation("Delete userHasActivity")
    @DeleteMapping("/user-has-activity/{userHasActivityId}")
    ResponseEntity<Void> deleteUserHasActivity(@PathVariable int userHasActivityId);

}
