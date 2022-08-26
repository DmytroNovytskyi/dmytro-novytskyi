package com.epam.spring.homework4.controller.api;

import com.epam.spring.homework4.controller.dto.CategoryDto;
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

@Api(tags = "Category management API")
@RequestMapping("api/v1/category")
public interface CategoryApi {

    @ApiOperation("Get all categories")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    List<CategoryDto> getAllCategories();

    @ApiImplicitParams(
            @ApiImplicitParam(name = "body",
                    paramType = "body",
                    required = true,
                    value = "Category to be created")
    )
    @ApiOperation("Create category")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    CategoryDto createCategory(@RequestBody @Validated(OnCreate.class) CategoryDto category);

    @ApiImplicitParams(
            @ApiImplicitParam(name = "body",
                    paramType = "body",
                    required = true,
                    value = "Category to be updated")
    )
    @ApiOperation("Update category")
    @ResponseStatus(HttpStatus.OK)
    @PutMapping
    CategoryDto updateCategory(@RequestBody @Validated(OnUpdate.class) CategoryDto category);

    @ApiImplicitParams(
            @ApiImplicitParam(name = "categoryId",
                    paramType = "path",
                    required = true,
                    value = "Id of category to be deleted")
    )
    @ApiOperation("Delete category")
    @DeleteMapping("/{categoryId}")
    ResponseEntity<Void> deleteCategory(@PathVariable int categoryId);

}
