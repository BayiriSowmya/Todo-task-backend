package com.iguruu.task;

import java.util.Set;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.iguruu.task.entity.Role;
import com.iguruu.task.entity.User;
import com.iguruu.task.repo.RoleRepository;
import com.iguruu.task.repo.UserRepository;

import jakarta.transaction.Transactional;

@SpringBootApplication
public class TodoApplication {

    public static void main(String[] args) {
        SpringApplication.run(TodoApplication.class, args);
    }

    @Bean
    @Transactional // Ensures database consistency
    public CommandLineRunner loadRolesAndAdmin(RoleRepository roleRepository, UserRepository userRepository, PasswordEncoder passwordEncoder) {
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
            String adminEmail = "sowmya@email.com";
            if (!userRepository.existsByEmail(adminEmail)) { 
                User admin = new User();
                admin.setFullname("Sowmya");
                admin.setUsername("sowmya15");
                admin.setPassword(passwordEncoder.encode("Sowmya15")); // ✅ Encode password
                admin.setEmail(adminEmail);

                // Assign ADMIN role
                admin.setRoles(Set.of(adminRole));

                userRepository.save(admin);
                System.out.println("✅ First ADMIN created successfully!");
            } else {
                System.out.println("⚠ Admin with email '" + adminEmail + "' already exists. Skipping creation.");
            }
        };
    }
}
