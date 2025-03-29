package com.iguruu.task.service;

import com.iguruu.task.entity.User;

import java.util.List;

public interface AdminService {
    List<User> getAllUsers();
    User getUserById(Long userId);
    void deleteUser(Long userId);
}