package com.epam.spring.homework6.controller.api;

import com.epam.spring.homework6.controller.dto.ActivityDto;
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

@Api(tags = "Activity management API")
@RequestMapping("api/v1/activity")
@Validated
public interface ActivityApi {

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
    @ApiOperation("Get page of sorted activities")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    Page<ActivityDto> getSortedPagedActivities(@RequestParam(defaultValue = "0")
                                               @Min(value = 0, message = "{activityApi.getSortedPagedActivities.page.min}")
                                                       int page,
                                               @RequestParam(defaultValue = "5")
                                               @Min(value = 0, message = "{activityApi.getSortedPagedActivities.size.min}")
                                                       int size,
                                               @RequestParam(defaultValue = "id")
                                               @Pattern(regexp = "id|name|status",
                                                       message = "{activityApi.getSortedPagedActivities.sortBy.pattern}")
                                                       String sortBy,
                                               @RequestParam(defaultValue = "asc")
                                               @Pattern(regexp = "asc|desc",
                                                       message = "{activityApi.getSortedPagedActivities.order.pattern}")
                                                       String order);

    @ApiImplicitParams(
            @ApiImplicitParam(name = "body",
                    paramType = "body",
                    required = true,
                    value = "Activity to be created")
    )
    @ApiOperation("Create activity")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    ActivityDto createActivity(@RequestBody @Validated(OnCreate.class) ActivityDto activity);

    @ApiImplicitParams(
            @ApiImplicitParam(name = "body",
                    paramType = "body",
                    required = true,
                    value = "Activity to be updated")
    )
    @ApiOperation("Update activity")
    @ResponseStatus(HttpStatus.OK)
    @PutMapping
    ActivityDto updateActivity(@RequestBody @Validated(OnUpdate.class) ActivityDto activity);

    @ApiImplicitParams(
            @ApiImplicitParam(name = "activityId",
                    paramType = "path",
                    required = true,
                    value = "Id of activity to be deleted")
    )
    @ApiOperation("Delete activity")
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/{activityId}")
    ResponseEntity<Void> deleteActivity(@PathVariable int activityId);

}
