package com.iguruu.task.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.iguruu.task.entity.User;
import com.iguruu.task.service.AdminService;


@RestController
@RequestMapping("/api/admin/users")
public class AdminController {

	 private final AdminService adminService;

	    public AdminController(AdminService adminService) {
	        this.adminService = adminService;
	    }

	    @GetMapping
	    public ResponseEntity<List<User>> getAllUsers() {
	        return ResponseEntity.ok(adminService.getAllUsers());
	    }

	    @GetMapping("/{userId}")
	    public ResponseEntity<User> getUserById(@PathVariable Long userId) {
	        return ResponseEntity.ok(adminService.getUserById(userId));
	    }

	    @DeleteMapping("/{userId}")
	    public ResponseEntity<String> deleteUser(@PathVariable Long userId) {
	        adminService.deleteUser(userId);
	        return ResponseEntity.ok("User deleted successfully");
	    }
}
