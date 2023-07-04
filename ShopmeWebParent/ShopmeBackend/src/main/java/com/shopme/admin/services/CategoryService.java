package com.shopme.admin.services;

import com.shopme.common.entity.Category;

import java.util.List;

public interface CategoryService {
    List<Category> getAll();

    Category createNew(Category category);
    Category getCategoryBySlug(String slug);

    Category getCategoryById(Integer id);

    void updateCategory(Category categoryUpdate);

    void deleteCategory(Integer id);
}
