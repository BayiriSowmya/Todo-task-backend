package com.iguruu.task.service.impl;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.iguruu.task.entity.User;
import com.iguruu.task.exception.TodoApiException;
import com.iguruu.task.repo.UserRepository;
import com.iguruu.task.service.UserService;


@Service
public class UserServiceImpl implements UserService {

	
	private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
	@Override
	public User getUserById(Long userId) {
		return userRepository.findById(userId)
                .orElseThrow(() -> new TodoApiException(HttpStatus.NOT_FOUND, "User not found"));
	}

	@Override
	public User updateUserProfile(Long userId, User updatedUser) {
		 User existingUser = getUserById(userId);
	        existingUser.setFullname(updatedUser.getFullname());
	        existingUser.setUsername(updatedUser.getUsername());
	        existingUser.setEmail(updatedUser.getEmail());
	        return userRepository.save(existingUser);
	    }

}
