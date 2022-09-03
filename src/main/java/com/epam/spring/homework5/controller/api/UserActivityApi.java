package com.epam.spring.homework5.controller.api;

import com.epam.spring.homework5.controller.dto.UserActivityDto;
import com.epam.spring.homework5.controller.dto.validation.group.OnCreate;
import com.epam.spring.homework5.controller.dto.validation.group.OnUpdate;
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

@Api(tags = "UserActivity management API")
@RequestMapping("api/v1/user-activity")
@Validated
public interface UserActivityApi {

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
    @ApiOperation("Get page of sorted userActivities")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    Page<UserActivityDto> getSortedPagedUserActivities(@RequestParam(defaultValue = "0")
                                                             @Min(value = 0, message = "{userActivityApi.getSortedPagedUserActivities.page.min}")
                                                                     int page,
                                                             @RequestParam(defaultValue = "5")
                                                             @Min(value = 0, message = "{userActivityApi.getSortedPagedUserActivities.size.min}")
                                                                     int size,
                                                             @RequestParam(defaultValue = "id")
                                                             @Pattern(regexp = "id|status|startTime|endTime|creationDate",
                                                                     message = "{userActivityApi.getSortedPagedUserActivities.sortBy.pattern}")
                                                                     String sortBy,
                                                             @RequestParam(defaultValue = "asc")
                                                             @Pattern(regexp = "asc|desc",
                                                                     message = "{userActivityApi.getSortedPagedUserActivities.order.pattern}")
                                                                     String order);

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
