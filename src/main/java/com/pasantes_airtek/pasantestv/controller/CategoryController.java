package com.pasantes_airtek.pasantestv.controller;

import com.pasantes_airtek.pasantestv.model.Category;
import com.pasantes_airtek.pasantestv.service.CategoryService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
@CrossOrigin(origins = "*")
public class CategoryController {
    private final CategoryService service;

    public CategoryController(CategoryService service) { this.service = service; }

    @PostMapping
    public Category save(@RequestBody Category category) { return service.save(category); }
    @DeleteMapping
    public void delete(@RequestBody Category category) { service.delete(category); }
    @GetMapping("/list")
    public List<Category> getCategoryList() { return service.findAll(); }
    @PostMapping("/id")
    public Category getCategoryById(@RequestBody Category category) { return service.findById(category.getId()); }
}
