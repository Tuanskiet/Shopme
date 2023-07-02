package com.shopme.admin.controllers;

import com.shopme.admin.services.UserAppService;
import com.shopme.common.entity.UserApp;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserRestController {
    private final UserAppService userAppService;

    @PostMapping("/user/check_email")
    public String checkEmailUnique(@Param("email") String email){
        UserApp userApp =  userAppService.getUserByEmail(email);
        return userApp == null ? "OK" : "DUPLICATED";
    }
}
