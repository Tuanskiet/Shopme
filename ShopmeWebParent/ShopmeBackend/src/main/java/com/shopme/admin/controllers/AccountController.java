package com.shopme.admin.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AccountController {

    @GetMapping("/login")
    String viewLoginForm() {
        return "user/login_page";
    }

    @PostMapping("/login")
    String doLogin() {
        return "redirect:/users";
    }
}
