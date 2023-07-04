package com.shopme.admin.database;

import com.shopme.admin.repository.RoleRepository;
import com.shopme.admin.repository.UserRepository;
import com.shopme.admin.repository.product.CategoryRepository;
import com.shopme.admin.repository.product.ProductRepository;
import com.shopme.admin.repository.product.ProductTypeRepository;
import com.shopme.admin.services.ProductService;
import com.shopme.admin.services.UserAppService;
import com.shopme.common.entity.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class initDTB implements CommandLineRunner {
    private final RoleRepository roleRepository;
    private final CategoryRepository categoryRepository;
    private final ProductTypeRepository productTypeRepository;
    private final UserAppService userAppService;
    private final ProductService productService;
    @Override
    public void run(String... args) throws Exception {
        RoleApp role1 = new RoleApp(null, "ROLE_USER", "permission");
        RoleApp role2 = new RoleApp(null, "ROLE_ADMIN", "permission");
        RoleApp role3 = new RoleApp(null, "ROLE_ASSISTANT", "permission");
        RoleApp role4 = new RoleApp(null, "ROLE_SHIPPER", "permission");
        roleRepository.saveAll(List.of(role1, role2,role3, role4));

        UserApp user1 = new UserApp(null, "tuankiet@gmail.com","123", "Tuan", "Kiet", null,true, null);
        UserApp user2 = new UserApp(null, "vanthanh@gmail.com","123", "Van", "Thanh", null,false, null);
        UserApp user3 = new UserApp(null, "tuanhia@gmail.com","123", "Tuan", "Chi", null,false, null);
        UserApp user4 = new UserApp(null, "trungahu@gmail.com","123", "Van", "Duc", null,true, null);
        userAppService.createNew(user1);
        userAppService.createNew(user2);
        userAppService.createNew(user3);
        userAppService.createNew(user4);

        //init category
        Category category1 = new Category(null, "Quần", "quan", null);
        Category category2 = new Category(null, "Áo", "ao", null);
        Category category3 = new Category(null, "Phụ kiện","phu-kien", null);
        Category category4 = new Category(null, "Test","test", null);
        categoryRepository.save(category1);
        categoryRepository.save(category2);
        categoryRepository.save(category3);
        categoryRepository.save(category4);

        //init product type
        ProductType productType1 = new ProductType(null, "Áo thun", "ao-thun", category2, null);
        ProductType productType2 = new ProductType(null, "Áo sơ mi", "ao-so-mi", category2, null);
        ProductType productType3 = new ProductType(null, "Quần Jean", "quan-jean", category1, null);
        productTypeRepository.save(productType1);
        productTypeRepository.save(productType2);
        productTypeRepository.save(productType3);

        Product product1 = new Product(null, "Áo thun T01", new BigDecimal("200000"), null, null,  "Cotton", 200, 23, "ao-thun-t01", productType1, new ArrayList<>());
        Product product2 = new Product(null, "Áo thun T01", new BigDecimal("200000"), null, null,  "Cotton", 200, 23, "ao-thun-t02", productType1, new ArrayList<>());
        Product product3 = new Product(null, "Áo thun T01", new BigDecimal("200000"), null, null,  "Cotton", 200, 23, "ao-thun-t03", productType1, new ArrayList<>());
        Product product4 = new Product(null, "Áo thun T01", new BigDecimal("200000"), null, null,  "Cotton", 200, 23, "ao-thun-t04", productType1, new ArrayList<>());
        Product product5 = new Product(null, "Áo thun T01", new BigDecimal("200000"), null, null,  "Cotton", 200, 23, "ao-thun-t05", productType1, new ArrayList<>());
        Product product6 = new Product(null, "Quan jean T92", new BigDecimal("200000"), null, null,  "Cotton", 200, 23, "quan-jean-t06", productType3, new ArrayList<>());

        productService.createNewProduct(product1);
        productService.createNewProduct(product2);
        productService.createNewProduct(product3);
        productService.createNewProduct(product4);
        productService.createNewProduct(product5);
        productService.createNewProduct(product6);
    }
}
