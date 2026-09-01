package com.pasantes_airtek.pasantestv.service;

import com.pasantes_airtek.pasantestv.model.Category;
import com.pasantes_airtek.pasantestv.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    private final CategoryRepository repository;

    public CategoryService(CategoryRepository repository) { this.repository = repository; }

    public Category save(Category category) { return repository.save(category); }
    public void delete(Category category) { repository.deleteById(category.getId()); }
    public List<Category> findAll() { return repository.findAll(); }
    public Category findById(Long id) { return repository.findById(id).orElse(null); }

}
