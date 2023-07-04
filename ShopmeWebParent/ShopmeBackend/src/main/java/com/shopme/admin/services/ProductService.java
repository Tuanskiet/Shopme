package com.shopme.admin.services;

import com.shopme.common.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {
    Product createNewProduct(Product product);

    List<Product> getAllProduct();

    Page<Product> getProductsWithSortAndPagination(Pageable pageable);

    Page<Product> getProductsByCategory(String category,Pageable pageable);

    Page<Product> getProductsByProductType(String productType, Pageable pageable);

    Product deleteProduct(Integer id);

    Product updateProduct(Integer id, Product newProduct);

    Product getProductById(Integer id);
}