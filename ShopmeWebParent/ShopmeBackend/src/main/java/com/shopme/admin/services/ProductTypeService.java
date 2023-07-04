package com.shopme.admin.services;

import com.shopme.common.entity.Category;
import com.shopme.common.entity.ProductType;

import java.util.List;

public interface ProductTypeService {

    List<ProductType> getAll();

    ProductType createNew(ProductType productType);

    ProductType getProductTypeById(Integer id);
    ProductType getProductTypeBySlug(String slug);

    void updateProductType(ProductType productTypeUD);

    void deleteProductType(Integer id);
}
