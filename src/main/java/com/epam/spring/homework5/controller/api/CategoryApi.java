package com.epam.spring.homework5.controller.api;

import com.epam.spring.homework5.controller.dto.CategoryDto;
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

@Api(tags = "Category management API")
@RequestMapping("api/v1")
@Validated
public interface CategoryApi {

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
    @ApiOperation("Get page of sorted categories")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/category")
    Page<CategoryDto> getSortedPagedCategories(@RequestParam(defaultValue = "0")
                                               @Min(value = 0, message = "{categoryApi.getSortedPagedCategories.page.min}")
                                                       int page,
                                               @RequestParam(defaultValue = "5")
                                               @Min(value = 0, message = "{categoryApi.getSortedPagedCategories.size.min}")
                                                       int size,
                                               @RequestParam(defaultValue = "id")
                                               @Pattern(regexp = "id|status",
                                                       message = "{categoryApi.getSortedPagedCategories.sortBy.pattern}")
                                                       String sortBy,
                                               @RequestParam(defaultValue = "asc")
                                               @Pattern(regexp = "asc|desc",
                                                       message = "{categoryApi.getSortedPagedCategories.order.pattern}")
                                                       String order);

    @ApiImplicitParams(
            @ApiImplicitParam(name = "body",
                    paramType = "body",
                    required = true,
                    value = "Category to be created")
    )
    @ApiOperation("Create category")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/category")
    CategoryDto createCategory(@RequestBody @Validated(OnCreate.class) CategoryDto category);

    @ApiImplicitParams(
            @ApiImplicitParam(name = "body",
                    paramType = "body",
                    required = true,
                    value = "Category to be updated")
    )
    @ApiOperation("Update category")
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/category")
    CategoryDto updateCategory(@RequestBody @Validated(OnUpdate.class) CategoryDto category);

    @ApiImplicitParams(
            @ApiImplicitParam(name = "categoryId",
                    paramType = "path",
                    required = true,
                    value = "Id of category to be deleted")
    )
    @ApiOperation("Delete category")
    @DeleteMapping("/category/{categoryId}")
    ResponseEntity<Void> deleteCategory(@PathVariable int categoryId);

}
