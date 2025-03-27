package com.iguruu.task.service;

import org.springframework.stereotype.Service;

import com.iguruu.task.entity.User;
@Service
public interface UserService {

	User getUserById(Long userId);
    User updateUserProfile(Long userId, User updatedUser);
}
