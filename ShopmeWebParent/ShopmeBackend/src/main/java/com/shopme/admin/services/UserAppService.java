package com.shopme.admin.services;

import com.shopme.common.entity.UserApp;
import org.springframework.security.core.userdetails.UserDetailsService;


import java.util.List;

public interface UserAppService extends UserDetailsService {

    UserApp createNew(UserApp userApp);
    List<UserApp> getAll();
    UserApp getUserById(Integer id);
    UserApp getUserByEmail(String email);
    void updateUser( UserApp newUser);
    UserApp deleteUser(Integer id);

}
