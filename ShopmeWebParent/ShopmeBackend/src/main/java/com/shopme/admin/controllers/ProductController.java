package com.shopme.admin.controllers;


import com.shopme.admin.domain.response.PageProductResponse;
import com.shopme.admin.domain.response.ResponseObject;
import com.shopme.admin.services.FirebaseService;
import com.shopme.admin.services.ProductImageService;
import com.shopme.admin.services.ProductService;
import com.shopme.admin.services.ProductTypeService;
import com.shopme.admin.utils.SlugGenerator;
import com.shopme.common.entity.Product;
import com.shopme.common.entity.ProductImage;
import com.shopme.common.entity.ProductType;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("product")
public class ProductController {
    @Autowired
    ProductService productService;
    @Autowired
    ProductTypeService productTypeService;
    @Autowired
    ProductImageService productImageService;
    @Autowired
    FirebaseService firebaseService;
    @GetMapping("/all")
    @Operation(summary="Get all product", description = "Get product in DB (limit 10P per page)")
    public String getAllProduct(Model model){
        List<Product> listProduct = productService.getAllProduct();
        model.addAttribute("listProduct", listProduct);
        return "product/list_product";
    }
    @GetMapping("/add")
    public String viewAddProductForm(Model model){
        model.addAttribute("newProduct", new Product());
        model.addAttribute("listProductType", this.getListProductType());
        return "/product/new_product";
    }


    @PostMapping("/new")
    public String createNewProduct(@ModelAttribute Product product,
                                   @RequestParam("mainImageFB") MultipartFile multipartFileMainIM,
                                   @RequestParam("subImage") MultipartFile multipartFileSubIM,
                                   @RequestParam("colorBySubImage") String colorBySubImage,
                                   RedirectAttributes redirectAttributes) throws IOException {
        product.setSlug(SlugGenerator.generateSlug(product.getName()));
        if(!multipartFileMainIM.isEmpty()){
            String folder = "products/" + product.getSlug() + "/" ;
            String linkImg = this.saveMainProductImageToFireBase(multipartFileMainIM, folder);
            product.setMainImage(linkImg);
        }
        if(!multipartFileSubIM.isEmpty()){
            String folder = "products/" + product.getSlug() + "/" + colorBySubImage + "/" ;
            String linkImg = this.saveMainProductImageToFireBase(multipartFileSubIM, folder);
            product.addExtraImage(linkImg, colorBySubImage);
        }
        productService.createNewProduct(product);
        redirectAttributes.addFlashAttribute("success", "Create product success!");
        return "redirect:/product/all";
    }

    private String saveMainProductImageToFireBase(MultipartFile multipartFile, String folder) throws IOException {
        return firebaseService.uploadFile(multipartFile, folder);
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
    public ResponseEntity<ResponseObject> updateProduct(
            @RequestParam(name="id") Integer id,
            @RequestBody Product newProduct){
        Product product = productService.updateProduct(id, newProduct);
        return ResponseEntity.ok().body(
                new ResponseObject("200", "Update product successfully", product)
        );
    }

    @DeleteMapping(value = "/delete")
    public ResponseEntity<ResponseObject> deleteProduct( @RequestParam(name="id") Integer id){
        Product product = productService.deleteProduct(id);
        return ResponseEntity.ok().body(
                new ResponseObject("200", "Delete product successfully", product)
        );
    }

    @PatchMapping(value = "/{id}/upload_img")
    public ResponseEntity<ResponseObject> uploadProductImage(
            @PathVariable(name="id") Integer id,
            @RequestBody ProductImage productImage){
        ProductImage productI = productImageService.insertProductImage(id, productImage);
        return ResponseEntity.ok().body(
                new ResponseObject("200", "Upload product image successfully", productI)
        );
    }
    private List<ProductType> getListProductType() {
        return productTypeService.getAll();
    }

}
