package com.iguruu.task.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.iguruu.task.dto.TaskDto;
import com.iguruu.task.entity.Task;
import com.iguruu.task.entity.User;
import com.iguruu.task.service.TaskService;
import com.iguruu.task.service.UserService;


@RestController
@RequestMapping("/api/user")
public class UserController {

	 private final TaskService taskService;
	    private final UserService userService;

	    public UserController(TaskService taskService, UserService userService) {
	        this.taskService = taskService;
	        this.userService = userService;
	    }

	    // 🟢 Get all tasks for a user
	    @GetMapping("/tasks/{userId}")
	    public ResponseEntity<List<Task>> getUserTasks(@PathVariable Long userId) {
	        List<Task> tasks = taskService.getTasksByUserId(userId);
	        return ResponseEntity.ok(tasks);
	    }

	    // 🟢 Update task status (Users can only update status)
	    @PutMapping("/tasks/{taskId}/status")
	    public ResponseEntity<Task> updateTaskStatus(
	            @PathVariable Long taskId,
	            @RequestBody TaskDto taskDto) {
	        Task updatedTask = taskService.updateTaskStatus(taskId, taskDto.getStatus());
	        return ResponseEntity.ok(updatedTask);
	    }

	    // 🟢 Get User Profile
	    @GetMapping("/profile/{userId}")
	    public ResponseEntity<User> getUserProfile(@PathVariable Long userId) {
	        User user = userService.getUserById(userId);
	        return ResponseEntity.ok(user);
	    }

	    // 🟢 Update User Profile
	    @PutMapping("/profile/{userId}")
	    public ResponseEntity<User> updateUserProfile(
	            @PathVariable Long userId, @RequestBody User updatedUser) {
	        User user = userService.updateUserProfile(userId, updatedUser);
	        return ResponseEntity.ok(user);
	    }
}
