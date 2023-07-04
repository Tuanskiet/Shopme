package com.shopme.admin.repository.product;

import com.shopme.common.entity.ProductType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductTypeRepository extends JpaRepository<ProductType, Integer> {
    ProductType findBySlug(String slug);
}
