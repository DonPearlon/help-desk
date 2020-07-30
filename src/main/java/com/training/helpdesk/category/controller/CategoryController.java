package com.training.helpdesk.category.controller;

import com.training.helpdesk.category.dto.CategoryDto;
import com.training.helpdesk.category.service.CategoryService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@PreAuthorize("hasAnyAuthority('EMPLOYEE','MANAGER','ENGINEER')")
@Api(tags = "3. Ticket categories")
public class CategoryController {
    private final CategoryService categoryService;
    
    /**
     * The constructor of the class.
     *
     * @param categoryService
     *            {@link CategoryService} - performs operations with ticket
     *            categories.
     */
    public CategoryController(final CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    /**
     * Returns the list of all ticket categories.
     *
     * @return {@link ResponseEntity}
     */
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get categories", notes = "${CategoryController.getCategories.notes}")
    public ResponseEntity<List<CategoryDto>> getCategories() {
        return  ResponseEntity.ok(categoryService.getCategories());
    }
}