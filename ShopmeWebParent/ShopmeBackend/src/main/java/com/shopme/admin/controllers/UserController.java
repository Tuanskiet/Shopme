package com.shopme.admin.controllers;

import com.shopme.admin.repository.RoleRepository;
import com.shopme.admin.services.UserAppService;
import com.shopme.admin.utils.FileUploadUtils;
import com.shopme.admin.utils.UserCsvExporter;
import com.shopme.admin.utils.UserExcelExporter;
import com.shopme.admin.utils.exportpdf.UserPdfExporter;
import com.shopme.common.entity.RoleApp;
import com.shopme.common.entity.UserApp;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class UserController {
    private final UserAppService userAppService;
    private final RoleRepository roleRepository;
    @GetMapping({"/","/users"})
    public String viewHomePage(Model model){
        model.addAttribute("listUser", this.getAllUser());
        return "index";
    }

    @GetMapping("/user/add")
    public String viewNewUserPage(Model model){
        model.addAttribute("listRole", this.getAllRole());
        model.addAttribute("newUser", new UserApp());
        return "user/new_user";
    }
    @PostMapping("/user/new")
    public String doCreateNewUser(
            @Validated @ModelAttribute("newUser") UserApp newUser,
            BindingResult bindingResult, Model model,
            RedirectAttributes redirectAttributes,
            @RequestParam("imageUser") MultipartFile multipartFile) throws IOException {
        //set addAttribute when have error
//        model.addAttribute("newUser",newUser);
        model.addAttribute("listRole", this.getAllRole()); //fix here

        //check error
        UserApp user =  userAppService.getUserByEmail(newUser.getEmail());
        if(user != null){ // email already exist
            model.addAttribute("duplicateEmail", true);
            return "user/new_user";
        }
        if(bindingResult.hasErrors()){
            for (Object object : bindingResult.getAllErrors()) {
                if(object instanceof FieldError) {
                    FieldError fieldError = (FieldError) object;
                    String atrName = fieldError.getField()+"Error";
                    model.addAttribute(atrName, fieldError.getDefaultMessage());
                }
            }
            return "user/new_user";
        }
        //save user photo // save user
        if(!multipartFile.isEmpty()){
            String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
            newUser.setPhotos(fileName);
            UserApp userApp =  userAppService.createNew(newUser);
            String uploadDir = "user-photos/"+userApp.getUserId();
            FileUploadUtils.cleanDir(uploadDir);
            FileUploadUtils.saveFile(uploadDir, fileName, multipartFile);
        }else{
            userAppService.createNew(newUser);
        }

        redirectAttributes.addFlashAttribute("success", "Create new user successfully!");
        return "redirect:/users";
    }
    @GetMapping("/user/edit")
    public String editUser(@RequestParam(name="id") Integer id,  Model model){
        UserApp userApp = userAppService.getUserById(id);
        model.addAttribute("userUpdate", userApp);
        return "user/edit_user";
    }
    @PostMapping("/user/update")
    public String updateUser(@RequestParam(name="id") Integer id,  Model model){
        UserApp userUpdate = (UserApp) model.getAttribute("userUpdate");
        userAppService.updateUser(id,userUpdate);
        model.addAttribute("success", "Update user successfully!");
        return "redirect:/users";
    }
    @GetMapping("/user/delete")
    public void deleteUser(@RequestParam(name="id") Integer id,  Model model){
        userAppService.deleteUser(id);
        model.addAttribute("success", "Delete user successfully!");
    }

    // export
    @GetMapping("/users/export/csv")
    public void exportToCsv(HttpServletResponse response) throws IOException {
        UserCsvExporter exporter = new UserCsvExporter();
        List<UserApp> list = this.getAllUser();
        exporter.export(list, response);
    }
    @GetMapping("/users/export/excel")
    public void exportToExcel(HttpServletResponse response) throws IOException {
        UserExcelExporter exporter = new UserExcelExporter();
        List<UserApp> list = this.getAllUser();
        exporter.export(list, response);
    }
    @GetMapping("/users/export/pdf")
    public void exportToPdf(HttpServletResponse response) throws IOException {
        UserPdfExporter exporter = new UserPdfExporter();
        List<UserApp> list = this.getAllUser();
        exporter.export(list, response);
    }



    private List<UserApp> getAllUser(){
        return userAppService.getAll();
    }
    private List<RoleApp> getAllRole() {
        return roleRepository.findAll();
    }

}
