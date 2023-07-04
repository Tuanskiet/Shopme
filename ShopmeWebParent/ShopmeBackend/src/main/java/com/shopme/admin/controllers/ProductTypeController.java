package com.shopme.admin.controllers;


import com.shopme.admin.domain.response.PageProductResponse;
import com.shopme.admin.domain.response.ResponseObject;
import com.shopme.admin.exception.AppException;
import com.shopme.admin.services.CategoryService;
import com.shopme.admin.services.ProductImageService;
import com.shopme.admin.services.ProductService;
import com.shopme.admin.services.ProductTypeService;
import com.shopme.admin.utils.SlugGenerator;
import com.shopme.admin.utils.export.csv.CategoryCsvExporter;
import com.shopme.admin.utils.export.csv.ProductTypeCsvExporter;
import com.shopme.common.entity.Category;
import com.shopme.common.entity.Product;
import com.shopme.common.entity.ProductImage;
import com.shopme.common.entity.ProductType;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("product-type")
@RequiredArgsConstructor
public class ProductTypeController {
    private final ProductTypeService productTypeService;
    private final CategoryService categoryService;
    @GetMapping("/all")
    public String viewAllProductType(Model model) {
        model.addAttribute("listProductType", this.getAllProductType());
        return "product_type/list_product_type";
    }

    @GetMapping("/add")
    public String viewNewProductType(Model model){
        model.addAttribute("newProductType", new ProductType());
        model.addAttribute("categories", this.getAllCategory());
        return "product_type/new_product_type";
    }
    @PostMapping("/new")
    public String doCreateNewProductType(
            @Validated @ModelAttribute("newProductType") ProductType productType,
            BindingResult bindingResult, Model model,
            RedirectAttributes redirectAttributes ){

        if(bindingResult.hasErrors()){
            for (Object object : bindingResult.getAllErrors()) {
                if(object instanceof FieldError) {
                    FieldError fieldError = (FieldError) object;
                    String atrName = fieldError.getField()+"Error";
                    model.addAttribute(atrName, fieldError.getDefaultMessage());
                }
            }
            return "product_type/new_product_type";
        }
        try{
            productTypeService.createNew(productType);
        }catch (AppException ex){
            model.addAttribute("error", ex.getMessage());
            return "product_type/new_product_type";
        }
        redirectAttributes.addFlashAttribute("success", "Create new category successfully!");
        return "redirect:/product-type/all";
    }

    @GetMapping("/edit")
    public String editProductType(@RequestParam(name="id") Integer id,  Model model){
        ProductType productType = productTypeService.getProductTypeById(id);
        model.addAttribute("productTypeUpdate", productType);
        model.addAttribute("categories", this.getAllCategory());
        return "product_type/edit_product_type";
    }
    @PostMapping("/update")
    public String updateUser(@ModelAttribute(name = "productTypeUpdate") ProductType productTypeUpdate, RedirectAttributes attributes){
        productTypeService.updateProductType(productTypeUpdate);
        attributes.addFlashAttribute("success", "Update type product successfully!");
        return "redirect:/product-type/all";
    }
    @GetMapping("/delete")
    public String deleteUser(@RequestParam(name="id") Integer id, Model model,  RedirectAttributes attributes){
        try{
            productTypeService.deleteProductType(id);
        }catch(AppException ex){
            attributes.addFlashAttribute("error", ex.getMessage());
            return "redirect:/product-type/all";
        }
        attributes.addFlashAttribute("success", "Delete type product successfully!");
        return "redirect:/product-type/all";
    }


    // export
    @GetMapping("/export/csv")
    public void exportToCsv(HttpServletResponse response) throws IOException {
        ProductTypeCsvExporter exporter = new ProductTypeCsvExporter();
        List<ProductType> list = this.getAllProductType();
        exporter.export(list, response);
    }

    private List<ProductType> getAllProductType() {
        return productTypeService.getAll();
    }
    private List<Category> getAllCategory() {
        return categoryService.getAll();
    }
}
