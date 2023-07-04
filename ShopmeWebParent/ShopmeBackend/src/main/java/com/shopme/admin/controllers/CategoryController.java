package com.shopme.admin.controllers;

import com.shopme.admin.exception.AppException;
import com.shopme.admin.services.CategoryService;
import com.shopme.admin.utils.FileUploadUtils;
import com.shopme.admin.utils.export.csv.CategoryCsvExporter;
import com.shopme.admin.utils.export.csv.UserCsvExporter;
import com.shopme.admin.utils.export.excel.UserExcelExporter;
import com.shopme.admin.utils.export.pdf.UserPdfExporter;
import com.shopme.common.entity.Category;
import com.shopme.common.entity.UserApp;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("category")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;
    @GetMapping("/all")
    public String viewAllCategory(Model model) {
        model.addAttribute("categories", this.getAllCategory());
        return "category/list_category";
    }

    @GetMapping("/add")
    public String viewNewCategory(Model model){
        model.addAttribute("newCategory", new Category());
        return "category/new_category";
    }
    @PostMapping("/new")
    public String doCreateNewCategory(
            @Validated @ModelAttribute("newCategory") Category category,
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
            return "category/new_category";
        }
        try{
            categoryService.createNew(category);
        }catch (AppException ex){
            model.addAttribute("error", ex.getMessage());
            return "category/new_category";
        }
        redirectAttributes.addFlashAttribute("success", "Create new category successfully!");
        return "redirect:/category/all";
    }

    @GetMapping("/edit")
    public String editCategory(@RequestParam(name="id") Integer id,  Model model){
        Category category = categoryService.getCategoryById(id);
        model.addAttribute("categoryUpdate", category);
        return "category/edit_category";
    }
    @PostMapping("/update")
    public String updateCategory(@ModelAttribute(name = "categoryUpdate") Category categoryUpdate, RedirectAttributes attributes){
        categoryService.updateCategory(categoryUpdate);
        attributes.addFlashAttribute("success", "Update category successfully!");
        return "redirect:/category/all";
    }
    @GetMapping("/delete")
    public String deleteCategory(@RequestParam(name="id") Integer id, Model model,  RedirectAttributes attributes){
        try{
            categoryService.deleteCategory(id);
        }catch(AppException ex){
            attributes.addFlashAttribute("error", ex.getMessage());
            return "redirect:/category/all";
        }
        attributes.addFlashAttribute("success", "Delete category successfully!");
        return "redirect:/category/all";
    }


    // export
    @GetMapping("/export/csv")
    public void exportToCsv(HttpServletResponse response) throws IOException {
        CategoryCsvExporter exporter = new CategoryCsvExporter();
        List<Category> list = this.getAllCategory();
        exporter.export(list, response);
    }

    private List<Category> getAllCategory() {
        return categoryService.getAll();
    }

}
