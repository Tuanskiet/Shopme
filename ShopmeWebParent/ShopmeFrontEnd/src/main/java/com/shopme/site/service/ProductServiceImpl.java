package com.shopme.site.service;

import com.shopme.common.entity.Product;
import com.shopme.site.repository.ProductRepository;
import exception.ProductException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    @Override
    public Product createNewProduct(Product product) {
        log.info("Saving product with id {}", product.getProductId());
        Product find = productRepository.findBySlug(product.getSlug());
        if(!ObjectUtils.isEmpty(find)){
            throw new ProductException("product already exist!");
        }
        return productRepository.save(product);
    }

    @Override
    public List<Product> getAllProduct() {
        return productRepository.findAll();
    }

    @Override
    public Page<Product> getProductsWithSortAndPagination(Pageable pageable) {
        return  productRepository.findAll(pageable);

    }

    @Override
    public Page<Product> getProductsByCategory(String category,Pageable pageable) {
        return productRepository.findByCategory(category, pageable);
    }

    @Override
    public Page<Product> getProductsByProductType(String productType, Pageable pageable) {
        return productRepository.findByProductType(productType, pageable);
    }

    @Override
    public Product deleteProduct(Integer id) {
        log.info("Deleting product with id {}",id);
        Optional<Product> product = productRepository.findById(id);
        if(!product.isPresent()){
            throw new ProductException("Product doe not exists");
        }
        productRepository.deleteById(id);
        return product.get();
    }

    @Override
    public Product updateProduct(Integer id,  Product newProduct) {
        log.info("Updating product with id {}",id);
        Optional<Product> product = productRepository.findById(id);
        if(!product.isPresent()){
            throw new ProductException("Product doe not exists");
        }
        product.map( prod -> {
            prod.setName(newProduct.getName());
            prod.setColors(newProduct.getColors());
            prod.setSizes(newProduct.getSizes());
            prod.setMaterial(newProduct.getMaterial());
            prod.setQuantityLeft(newProduct.getQuantityLeft());
            prod.setQuantitySold(newProduct.getQuantitySold());
            prod.setDateRelease(newProduct.getDateRelease());
            prod.setSlug(newProduct.getSlug());
            prod.setProductType(newProduct.getProductType());
            prod.setImages(newProduct.getImages());
            return productRepository.save(prod);
        });
        return newProduct;
    }

    @Override
    public Product getProductById(Integer id) {
        return productRepository.findById(id).orElseThrow(() -> new ProductException("Could not found Product with id " + id));
    }


}