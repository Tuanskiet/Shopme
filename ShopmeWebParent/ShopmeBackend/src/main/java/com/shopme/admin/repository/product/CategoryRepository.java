package com.shopme.admin.repository.product;

import com.shopme.common.entity.Category;
import com.shopme.common.entity.UserApp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
    Category findBySlug(String slug);
}
