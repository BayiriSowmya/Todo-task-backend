package com.iguruu.task.service;

import com.iguruu.task.entity.User;

public interface UserService {
    User getUserById(Long userId);
    User updateUserProfile(Long userId, User updatedUser);
}