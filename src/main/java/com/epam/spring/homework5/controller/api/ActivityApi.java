package com.epam.spring.homework5.controller.api;

import com.epam.spring.homework5.controller.dto.ActivityDto;
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

@Api(tags = "Activity management API")
@RequestMapping("api/v1")
public interface ActivityApi {

    @ApiOperation("Get all activities")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/activity")
    List<ActivityDto> getAllActivities();

    @ApiImplicitParams(
            @ApiImplicitParam(name = "body",
                    paramType = "body",
                    required = true,
                    value = "Activity to be created")
    )
    @ApiOperation("Create activity")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/activity")
    ActivityDto createActivity(@RequestBody @Validated(OnCreate.class) ActivityDto activity);

    @ApiImplicitParams(
            @ApiImplicitParam(name = "body",
                    paramType = "body",
                    required = true,
                    value = "Activity to be updated")
    )
    @ApiOperation("Update activity")
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/activity")
    ActivityDto updateActivity(@RequestBody @Validated(OnUpdate.class) ActivityDto activity);

    @ApiImplicitParams(
            @ApiImplicitParam(name = "activityId",
                    paramType = "path",
                    required = true,
                    value = "Id of activity to be deleted")
    )
    @ApiOperation("Delete activity")
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/activity/{activityId}")
    ResponseEntity<Void> deleteActivity(@PathVariable int activityId);

}
