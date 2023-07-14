package com.shopme.admin.services.Impl;

import com.shopme.admin.repository.UserRepository;

import com.shopme.admin.security.MyUserDetails;
import com.shopme.admin.services.UserAppService;
import com.shopme.common.entity.UserApp;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserAppServiceImpl implements UserAppService{
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public final int SIZE_PRODUCT_PER_PAGE = 1;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserApp userApp = userRepository.getUserByEmail(username);
        if(userApp == null){
            throw new UsernameNotFoundException("User not found!");
        }
        return new MyUserDetails(userApp);
    }

    @Override
    public UserApp createNew(UserApp userApp) {
        UserApp findUser =  userRepository.findByEmail(userApp.getEmail());
        this.encodePassword(userApp);
        if(findUser != null){
            throw new RuntimeException("User already exists!");
        }
        return userRepository.save(userApp);
    }

    @Override
    public List<UserApp> getAll() {
        return userRepository.findAll();
    }

    @Override
    public Page<UserApp> getUserByPage(int page) {
        Pageable pageable = PageRequest.of(page -1, SIZE_PRODUCT_PER_PAGE);
        return userRepository.findAll(pageable);
    }

    @Override
    public UserApp getUserById(Integer id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Could not found user with id :"+ id));
    }

    @Override
    public UserApp getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public void updateUser(UserApp newUser) {
        UserApp oldUser = this.getUserById(newUser.getUserId());
        BeanUtils.copyProperties(newUser, oldUser);
        this.encodePassword(oldUser);
        userRepository.save(oldUser);
    }

    @Override
    public UserApp deleteUser(Integer id) {
        UserApp userDelete = this.getUserById(id);
        userRepository.deleteById(id);
        return userDelete;
    }

    @Override
    public List<UserApp> saveAll(List<UserApp> list) {
        return userRepository.saveAll(list);
    }


    private void encodePassword(UserApp userApp) {
        String pEncode = passwordEncoder.encode(userApp.getPassword());
        userApp.setPassword(pEncode);
    }


}
