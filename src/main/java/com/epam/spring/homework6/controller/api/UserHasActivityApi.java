package com.epam.spring.homework6.controller.api;

import com.epam.spring.homework6.controller.dto.UserHasActivityDto;
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

@Api(tags = "UserHasActivity management API")
@RequestMapping("api/v1")
@Validated
public interface UserHasActivityApi {

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
    @ApiOperation("Get page of sorted userHasActivities")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/user-has-activity")
    Page<UserHasActivityDto> getSortedPagedUserHasActivities(@RequestParam(defaultValue = "0")
                                                             @Min(value = 0, message = "{userHasActivityApi.getSortedPagedUserHasActivities.page.min}")
                                                                     int page,
                                                             @RequestParam(defaultValue = "5")
                                                             @Min(value = 0, message = "{userHasActivityApi.getSortedPagedUserHasActivities.size.min}")
                                                                     int size,
                                                             @RequestParam(defaultValue = "id")
                                                             @Pattern(regexp = "id|status|startTime|endTime|creationDate",
                                                                     message = "{userHasActivityApi.getSortedPagedUserHasActivities.sortBy.pattern}")
                                                                     String sortBy,
                                                             @RequestParam(defaultValue = "asc")
                                                             @Pattern(regexp = "asc|desc",
                                                                     message = "{userHasActivityApi.getSortedPagedUserHasActivities.order.pattern}")
                                                                     String order);

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
