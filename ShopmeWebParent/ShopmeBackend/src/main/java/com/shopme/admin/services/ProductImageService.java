package com.shopme.admin.services;

import com.shopme.common.entity.ProductImage;

public interface ProductImageService {
    ProductImage insertProductImage(Integer productId, ProductImage productImage);

}
