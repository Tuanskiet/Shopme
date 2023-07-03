package com.shopme.admin.database;

import com.shopme.admin.repository.RoleRepository;
import com.shopme.admin.repository.UserRepository;
import com.shopme.common.entity.RoleApp;
import com.shopme.common.entity.UserApp;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class initDTB implements CommandLineRunner {
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    @Override
    public void run(String... args) throws Exception {
        RoleApp role1 = new RoleApp(null, "ROLE_USER", "permission");
        RoleApp role2 = new RoleApp(null, "ROLE_ADMIN", "permission");
        RoleApp role3 = new RoleApp(null, "ROLE_ASSISTANT", "permission");
        RoleApp role4 = new RoleApp(null, "ROLE_SHIPPER", "permission");
        roleRepository.saveAll(List.of(role1, role2,role3, role4));

        UserApp user1 = new UserApp(null, "tuankiet@gmail.com","123", "Tuan", "Kiet", null,true, null);
        UserApp user2 = new UserApp(null, "vanthanh@gmail.com","123", "Van", "Thanh", null,false, null);
        UserApp user3 = new UserApp(null, "tuanhia@gmail.com","123", "Tuan", "Chi", null,false, null);
        UserApp user4 = new UserApp(null, "trungahu@gmail.com","123", "Van", "Duc", null,true, null);
        userRepository.saveAll(List.of(user1, user2, user3, user4));
    }
}
