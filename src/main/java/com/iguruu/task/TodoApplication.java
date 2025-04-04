package com.iguruu.task;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.Transactional;

import com.iguruu.task.entity.Role;
import com.iguruu.task.entity.User;
import com.iguruu.task.repo.RoleRepository;
import com.iguruu.task.repo.UserRepository;

import java.util.Set;

@SpringBootApplication
public class TodoApplication {

    public static void main(String[] args) {
        SpringApplication.run(TodoApplication.class, args);
    }

    @Bean
    @Transactional // Ensures database consistency
    public CommandLineRunner loadRolesAndAdmin(RoleRepository roleRepository, UserRepository userRepository) {
        return args -> {
            // ✅ Create roles if they don't exist
            Role adminRole = roleRepository.findByRolename("ADMIN").orElseGet(() -> {
                Role role = new Role();
                role.setRolename("ADMIN");
                return roleRepository.save(role);
            });

            Role userRole = roleRepository.findByRolename("USER").orElseGet(() -> {
                Role role = new Role();
                role.setRolename("USER");
                return roleRepository.save(role);
            });

            // ✅ Prevent duplicate admin creation by checking email
            if (!userRepository.existsByEmail("sowmya@gmail.com")) { 
                User admin = new User();
                admin.setFullname("sowmya");
                admin.setUsername("sowmya12");
                admin.setPassword("$2a$12$TFngrDBCCChNU9/zIrinQ.FxkknoPwZX2ACPNKwRzQ8SrFQXnPDWm"); // ⚠ No encoding as per your request
                admin.setEmail("sowmya@gmail.com");

                // Assign ADMIN role
                admin.setRoles(Set.of(adminRole));

                userRepository.save(admin);
                System.out.println("✅ First ADMIN created successfully!");
            } else {
                System.out.println("⚠ Admin with email 'sowmya@email.com' already exists. Skipping creation.");
            }
        };
    }
}