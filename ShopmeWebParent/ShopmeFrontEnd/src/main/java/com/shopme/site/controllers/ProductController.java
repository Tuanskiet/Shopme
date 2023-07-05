package com.shopme.site.controllers;


import com.shopme.common.entity.Product;
import com.shopme.site.PageProductResponse;
import com.shopme.site.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
public class ProductController {
    @Autowired
    ProductService productService;
    @GetMapping({"/index", "/"})
    String viewIndexPage(Model model) {
        List<Product> listProduct = productService.getAllProduct();
        model.addAttribute("prodBestSeller", listProduct);
        model.addAttribute("prodDiscount", listProduct);
        return "index";
    }

//    private List<Product> getProductDiscount() {
//    }
//
//    private Object getProductBestSeller() {
//
//    }

    @GetMapping("/all")
    public String getAllProduct(Model model){
        List<Product> listProduct = productService.getAllProduct();
        model.addAttribute("listProduct", listProduct);
        return "product/list_product";
    }
    @GetMapping("add")
    public String viewAddProductForm(){
        return "/product/new_product";
    }

    @PostMapping("/new")
    public String createNewProduct(@RequestBody Product product){
//        product.setSlug(SlugGenerator.generateSlug(product.getName()));
        productService.createNewProduct(product);
        return "";
    }

    @GetMapping("")
    public ResponseEntity<PageProductResponse> getProductsWithSortAndPagination(
            @RequestParam(name="page",  defaultValue = "0",     required = false) int page,
            @RequestParam(name="size",  defaultValue = "5",     required = false) int size,
            @RequestParam(name="sortBy",defaultValue = "id", required = false) String sortBy,
            @RequestParam(name="order", defaultValue = "asc",   required = false) String orderBy){
        Pageable pageable = PageRequest.of(page, size).withSort(Sort.by(Sort.Direction.fromString(orderBy), sortBy));
        Page<Product> products =  productService.getProductsWithSortAndPagination(pageable);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new PageProductResponse("ok",  products.getTotalElements(), products));
    }

    @GetMapping("/{category}")
    public ResponseEntity<PageProductResponse> getProductsByCategory(
            @PathVariable(name ="category") String category,
            @RequestParam(name="page",  defaultValue = "0",      required = false) int page,
            @RequestParam(name="size",  defaultValue = "5",      required = false) int size,
            @RequestParam(name="sortBy",defaultValue = "id",  required = false) String sortBy,
            @RequestParam(name="order", defaultValue = "asc",    required = false) String orderBy){
        Pageable pageable = PageRequest.of(page, size).withSort(Sort.by(Sort.Direction.fromString(orderBy), sortBy));
        Page<Product> products =  productService.getProductsByCategory(category,pageable);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new PageProductResponse("ok",  products.getTotalElements(), products));
    }

    @GetMapping("/{category}/{productType}")
    public ResponseEntity<PageProductResponse> getProductsByProductType(
            @PathVariable(name ="category") String category,
            @PathVariable(name ="productType") String productType,
            @RequestParam(name="page",  defaultValue = "0",      required = false) int page,
            @RequestParam(name="size",  defaultValue = "5",      required = false) int size,
            @RequestParam(name="sortBy",defaultValue = "id",  required = false) String sortBy,
            @RequestParam(name="order", defaultValue = "asc",    required = false) String orderBy){
        Pageable pageable = PageRequest.of(page, size).withSort(Sort.by(Sort.Direction.fromString(orderBy), sortBy));
        Page<Product> products =  productService.getProductsByProductType(productType,pageable);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new PageProductResponse("ok",  products.getTotalElements(), products));
    }
    @PutMapping(value = "/update")
    public String updateProduct(
            @RequestParam(name="id") Integer id,
            @RequestBody Product newProduct){
        Product product = productService.updateProduct(id, newProduct);
        return "";
    }

    @DeleteMapping(value = "/delete")
    public String deleteProduct( @RequestParam(name="id") Integer id){
        Product product = productService.deleteProduct(id);
        return "";
    }

}
