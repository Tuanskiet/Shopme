package com.shopme.admin.services.Impl;

import com.shopme.admin.exception.ProductException;
import com.shopme.admin.repository.product.ProductImageRepository;
import com.shopme.admin.repository.product.ProductRepository;
import com.shopme.admin.services.ProductImageService;
import com.shopme.common.entity.Product;
import com.shopme.common.entity.ProductImage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductImageServiceImpl implements ProductImageService {
    private final ProductRepository productRepository;
    private final ProductImageRepository productImageRepository;
    @Override
    public ProductImage insertProductImage(Integer productId, ProductImage productImage) {
        log.info("Inserting productImage with productId {}",productId);
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductException("Product does not exist"));
//        List<ProductImage> productImages = product.getImages();
        productImage.setProduct(product);
//        productImages.add(productImage);
//        product.setImages(productImages);
        productImageRepository.save(productImage);
        return productImage;
    }
}