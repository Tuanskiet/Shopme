package com.shopme.admin.services.Impl;

import com.shopme.admin.exception.AppException;
import com.shopme.admin.repository.product.CategoryRepository;
import com.shopme.admin.services.CategoryService;
import com.shopme.common.entity.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    @Override
    public List<Category> getAll() {
        return categoryRepository.findAll();
    }

    @Override
    public Category createNew(Category category) {
        if(getCategoryBySlug(category.getSlug()) != null){
            throw new AppException("Category already Exist!");
        }
        return categoryRepository.save(category);
    }

    @Override
    public Category getCategoryBySlug(String slug) {
        return categoryRepository.findBySlug(slug);
    }

    @Override
    public Category getCategoryById(Integer id){
        return categoryRepository.findById(id).get();
    }

    @Override
    public void updateCategory(Category categoryUpdate) {
        Optional<Category> oldCategory = categoryRepository.findById(categoryUpdate.getCategoryId());
        if(!oldCategory.isPresent()){
            throw new AppException("Could not found category with id : "+ categoryUpdate.getCategoryId());
        }
        BeanUtils.copyProperties(categoryUpdate, oldCategory.get());
        categoryRepository.save(oldCategory.get());
    }

    @Override
    public void deleteCategory(Integer id) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new AppException("Could not found category with id : " + id));
        if(category.getProductTypes().size() > 0){
            throw new AppException("Couldn't delete this category because have constrains with type product!");
        }
        categoryRepository.deleteById(id);
    }
}
