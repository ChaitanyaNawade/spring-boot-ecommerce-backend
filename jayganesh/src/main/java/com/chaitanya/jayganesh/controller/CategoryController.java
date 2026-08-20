package com.chaitanya.jayganesh.controller;

import com.chaitanya.jayganesh.entity.Category;
import com.chaitanya.jayganesh.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/categories")
public class CategoryController
{
    @Autowired
    private CategoryService categoryService;

    @PostMapping()
    public Category createCategory(@RequestBody Category category)
    {
        return categoryService.createCategory(category);
    }

    @GetMapping()
    public List<Category> getAllCategories()
    {
        return categoryService.getAllCategories();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Category> getCategoryById(@PathVariable Long id)
    {
        return categoryService.getCategoryById(id)
                .map(ResponseEntity::ok).
                orElse(ResponseEntity.notFound().
                        build());
    }

    @DeleteMapping("/{id}")
    public String deleteCategory(@PathVariable Long id)
    {
         categoryService.deleteCategory(id);
         return  "category deleted";
    }
}
