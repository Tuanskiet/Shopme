package com.shopme.admin.repository;

import com.shopme.common.entity.UserApp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserApp, Integer> {
    UserApp findByEmail(String email);

    UserApp getUserByEmail(String username);
}
