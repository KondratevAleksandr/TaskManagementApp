package com.example.TaskManagementApp.Service;

import com.example.TaskManagementApp.entity.Category;
import com.example.TaskManagementApp.exceptions.CategoryNotFoundException;
import com.example.TaskManagementApp.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public Category createCategory(Category category) {
        return categoryRepository.save(category);
    }

    public Category getCategoryById(Integer id) {
        return categoryRepository.findById(id).orElseThrow(
                () -> new CategoryNotFoundException("Category not found with id " + id));
    }

    public Category updateCategory(Integer id, Category category) {
        Category existingCategory = getCategoryById(id);
        existingCategory.setName(category.getName());
        return categoryRepository.save(existingCategory);
    }

    public void deleteCategory(Integer id) {
        if (!existsById(id)) {
            throw new CategoryNotFoundException("Category not found with id " + id);
        }
        categoryRepository.deleteById(id);
    }

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public boolean existsById(Integer id) {
        return categoryRepository.existsById(id);
    }
}