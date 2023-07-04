package com.shopme.admin.repository.product;

import com.shopme.common.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    Product findBySlug(String slug);

    @Query("SELECT p FROM Product p JOIN p.productType pt JOIN pt.category c WHERE c.slug = :category")
    Page<Product> findByCategory(@Param("category") String category, Pageable pageable);

    @Query("SELECT p FROM Product p JOIN p.productType pt WHERE pt.slug = :productType")
    Page<Product> findByProductType(String productType, Pageable pageable);

}