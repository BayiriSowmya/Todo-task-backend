package com.iguruu.task.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.iguruu.task.entity.User;
@Service
public interface AdminService {

	List<User> getAllUsers();
    User getUserById(Long userId);
    void deleteUser(Long userId);
}
