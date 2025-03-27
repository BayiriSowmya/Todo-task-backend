package com.iguruu.task.service.impl;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.iguruu.task.config.JwtUtil;
import com.iguruu.task.dto.AuthResponseDto;
import com.iguruu.task.dto.LoginDto;
import com.iguruu.task.dto.UserDto;
import com.iguruu.task.entity.Role;
import com.iguruu.task.entity.User;
import com.iguruu.task.exception.TodoApiException;
import com.iguruu.task.mapper.UserMapper;
import com.iguruu.task.repo.RoleRepository;
import com.iguruu.task.repo.UserRepository;
import com.iguruu.task.service.AuthService;

@Service
public class AuthImpl implements AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public String register(UserDto userDto) {
        if (userDto == null) {
            throw new TodoApiException(HttpStatus.BAD_REQUEST, "User data cannot be null");
        }

        if (userRepository.existsByUsername(userDto.getUsername())) {
            throw new TodoApiException(HttpStatus.BAD_REQUEST, "Username already exists");
        }
        if (userRepository.existsByEmail(userDto.getEmail())) {
            throw new TodoApiException(HttpStatus.BAD_REQUEST, "Email already exists");
        }

        // Assign USER role by default
        Role userRole = roleRepository.findByRolename("USER")
            .orElseThrow(() -> new TodoApiException(HttpStatus.INTERNAL_SERVER_ERROR, "USER role not found!"));

        // Map user data and assign default USER role
        User user = UserMapper.mapToEntity(userDto, null);
        user.setPassword(passwordEncoder.encode(userDto.getPassword())); // ✅ Encode Password
        user.setRoles(Set.of(userRole)); // Assign USER role

        userRepository.save(user);
        return "✅ User registered successfully!";
    }

    @Override
    public AuthResponseDto login(LoginDto loginDto) {
        // ✅ Improved input validation to prevent null & empty values
        if (loginDto == null || loginDto.getUsername() == null || loginDto.getUsername().trim().isEmpty() 
                || loginDto.getPassword() == null || loginDto.getPassword().trim().isEmpty()) {
            throw new TodoApiException(HttpStatus.BAD_REQUEST, "Username or password cannot be null or empty");
        }

        User user = userRepository.findByUsername(loginDto.getUsername())
                .orElseThrow(() -> new TodoApiException(HttpStatus.NOT_FOUND, "User not found"));

        if (!passwordEncoder.matches(loginDto.getPassword(), user.getPassword())) {
            throw new TodoApiException(HttpStatus.UNAUTHORIZED, "Invalid username or password!");
        }

        // ✅ Generate Access & Refresh Tokens
        String accessToken = jwtUtil.generateToken(user.getUsername(), user.getRoles().iterator().next().getRolename());
        String refreshToken = jwtUtil.generateRefreshToken(user.getUsername());

        // ✅ Return structured response with user details
        return new AuthResponseDto(
                accessToken,
                refreshToken,
                user.getId(),
                user.getUsername(),
                user.getRoles().iterator().next().getRolename()
        );
    }

    @Override
    public String registerAdmin(UserDto userDto, Long adminId) {
        if (userDto == null || adminId == null) {
            throw new TodoApiException(HttpStatus.BAD_REQUEST, "Invalid input data");
        }

        // Verify that the requesting user (adminId) is an ADMIN
        User adminUser = userRepository.findById(adminId)
            .orElseThrow(() -> new TodoApiException(HttpStatus.FORBIDDEN, "Admin not found!"));

        boolean isAdmin = adminUser.getRoles().stream()
            .anyMatch(role -> role.getRolename().equals("ADMIN"));

        if (!isAdmin) {
            throw new TodoApiException(HttpStatus.FORBIDDEN, "Only an ADMIN can create another ADMIN!");
        }

        // Check if the new admin's username and email already exist
        if (userRepository.existsByUsername(userDto.getUsername())) {
            throw new TodoApiException(HttpStatus.BAD_REQUEST, "Username already exists");
        }
        if (userRepository.existsByEmail(userDto.getEmail())) {
            throw new TodoApiException(HttpStatus.BAD_REQUEST, "Email already exists");
        }

        // Assign ADMIN role
        Role adminRole = roleRepository.findByRolename("ADMIN")
            .orElseThrow(() -> new TodoApiException(HttpStatus.INTERNAL_SERVER_ERROR, "ADMIN role not found!"));

        // Map user data and assign ADMIN role
        User newAdmin = UserMapper.mapToEntity(userDto, null);
        newAdmin.setPassword(passwordEncoder.encode(userDto.getPassword())); // ✅ Encode Password
        newAdmin.setRoles(Set.of(adminRole)); // Assign ADMIN role

        userRepository.save(newAdmin);
        return "✅ Admin registered successfully!";
    }
}
