package com.shopme.admin.repository;

import com.shopme.common.entity.RoleApp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<RoleApp, Integer> {
}
