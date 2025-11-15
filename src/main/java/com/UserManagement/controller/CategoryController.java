package com.UserManagement.controller;

import com.UserManagement.dto.CategoryDTO;
import com.UserManagement.exceptions.Response;
import com.UserManagement.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping("/createCategory")
    public ResponseEntity <?> createCategory(@RequestBody CategoryDTO categoryDTO) {
        Response createCategory = categoryService.createCategory(categoryDTO);
        return ResponseEntity.ok(createCategory);
    }

    @PutMapping("/updateCategory/{id}")
    public ResponseEntity <?> updateCategory(@PathVariable Long id, @RequestBody CategoryDTO categoryDTO) {
        Response updateCategory = categoryService.updateCategory(id, categoryDTO);
        return ResponseEntity.ok(updateCategory);
    }

    @DeleteMapping("/deleteCategory/{id}")
    public ResponseEntity <?> deleteCategory(@PathVariable Long id) {
        Response deleteCategory = categoryService.deleteCategory(id);
        return ResponseEntity.ok(deleteCategory);
    }

    @GetMapping("/getCategoryById/{id}")
    public ResponseEntity <?> getCategoryById(@PathVariable Long id) {
        Response getCategoryById = categoryService.getCategoryById(id);
        return ResponseEntity.ok(getCategoryById);
    }

    @GetMapping("/getAllCategories")
    public ResponseEntity <?> getAllCategories() {
        Response allCategories = categoryService.getAllCategories();
        return ResponseEntity.ok(allCategories);
    }
}
