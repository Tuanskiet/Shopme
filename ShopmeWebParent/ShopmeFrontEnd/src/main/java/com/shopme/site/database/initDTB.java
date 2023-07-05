package com.shopme.site.database;

import com.shopme.common.entity.*;
import com.shopme.site.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;
import java.util.HashSet;


@Component
@RequiredArgsConstructor
public class initDTB implements CommandLineRunner {
    private final ProductService productService;
    @Override
    public void run(String... args) throws Exception {

        Product product1 = new Product(null, "Áo thun T01", new BigDecimal("200000"), null, null,  "Cotton", 200, 23, "ao-thun-t01", null, new HashSet<>());
        Product product2 = new Product(null, "Áo thun T01", new BigDecimal("200000"), null, null,  "Cotton", 200, 23, "ao-thun-t02", null, new HashSet<>());
        Product product3 = new Product(null, "Áo thun T01", new BigDecimal("200000"), null, null,  "Cotton", 200, 23, "ao-thun-t03", null, new HashSet<>());
        Product product4 = new Product(null, "Áo thun T01", new BigDecimal("200000"), null, null,  "Cotton", 200, 23, "ao-thun-t04", null, new HashSet<>());
        Product product5 = new Product(null, "Áo thun T01", new BigDecimal("200000"), null, null,  "Cotton", 200, 23, "ao-thun-t05", null, new HashSet<>());
        Product product6 = new Product(null, "Quan jean T92", new BigDecimal("200000"), null, null,  "Cotton", 200, 23, "quan-jean-t06", null, new HashSet<>());

        product1.setMainImage("images/product/AT02/black/p1.png");
        productService.createNewProduct(product1);
        productService.createNewProduct(product2);
        productService.createNewProduct(product3);
        productService.createNewProduct(product4);
        productService.createNewProduct(product5);
        productService.createNewProduct(product6);
    }
}
