package com.shopme.admin.services.Impl;

import com.shopme.admin.exception.AppException;
import com.shopme.admin.repository.product.ProductTypeRepository;
import com.shopme.admin.services.ProductTypeService;
import com.shopme.common.entity.ProductType;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductTypeServiceImpl implements ProductTypeService {
    private final ProductTypeRepository productTypeRepository;
    @Override
    public List<ProductType> getAll() {
        return productTypeRepository.findAll();
    }

    @Override
    public ProductType createNew(ProductType productType) {
        if(getProductTypeBySlug(productType.getSlug()) != null){
            throw new AppException("Product type already exist!");
        }
        return productTypeRepository.save(productType);
    }

    @Override
    public ProductType getProductTypeById(Integer id) {
        return productTypeRepository.findById(id).get();
    }

    @Override
    public ProductType getProductTypeBySlug(String slug) {
        return productTypeRepository.findBySlug(slug);
    }

    @Override
    public void updateProductType(ProductType productTypeUD) {
        Optional<ProductType> oldProductType = productTypeRepository.findById(productTypeUD.getProductTypeId());
        if(!oldProductType.isPresent()){
            throw new AppException("Could not found product type with id : "+ productTypeUD.getProductTypeId());
        }
        BeanUtils.copyProperties(productTypeUD, oldProductType.get());
        productTypeRepository.save(oldProductType.get());
    }

    @Override
    public void deleteProductType(Integer id) {
        ProductType productType = productTypeRepository.findById(id).orElseThrow(() -> new AppException("Could not found product type with id : " + id));
        if(productType.getListProduct().size() > 0){
            throw new AppException("Couldn't delete this type because it have constrains with product!");
        }
        productTypeRepository.deleteById(id);
    }
}
