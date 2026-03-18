package com.newtome.newtomeapi.catalog.controller;

import com.newtome.newtomeapi.catalog.dto.CategoryResponse;
import com.newtome.newtomeapi.catalog.service.CategoryService;
import com.newtome.newtomeapi.common.ApiResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public ApiResponse<List<CategoryResponse>> getAllCategories() {

        List<CategoryResponse> categories = categoryService.getAllCategories();

        return new ApiResponse<>(true, "Categories loaded", categories);
    }


}
