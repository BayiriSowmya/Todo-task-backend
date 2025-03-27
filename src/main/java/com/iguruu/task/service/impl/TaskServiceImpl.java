package com.iguruu.task.service.impl;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.iguruu.task.dto.TaskDto;
import com.iguruu.task.entity.Task;
import com.iguruu.task.entity.TaskStatus;
import com.iguruu.task.entity.User;
import com.iguruu.task.exception.TodoApiException;
import com.iguruu.task.repo.TaskRepository;
import com.iguruu.task.repo.UserRepository;
import com.iguruu.task.service.TaskService;

@Service
public class TaskServiceImpl implements TaskService {

    private static final Logger logger = LoggerFactory.getLogger(TaskServiceImpl.class);
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    public TaskServiceImpl(TaskRepository taskRepository, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Task assignTask(TaskDto taskDto) {
        logger.info("Assigning task to user with ID: {}", taskDto.getUserId());
        
        User user = userRepository.findById(taskDto.getUserId())
                .orElseThrow(() -> new TodoApiException(HttpStatus.NOT_FOUND, "User not found"));

        Task task = new Task();
        task.setTask(taskDto.getTask());

        // Convert String status to Enum with error handling
        task.setStatus(parseTaskStatus(taskDto.getStatus()));
        task.setUser(user);

        Task savedTask = taskRepository.save(task);
        logger.info("Task assigned successfully with ID: {}", savedTask.getId());
        return savedTask;
    }

    @Override
    public List<Task> getTasksByUserId(Long userId) {
        logger.info("Fetching tasks for user with ID: {}", userId);
        return taskRepository.findByUserId(userId);
    }

    @Override
    public Task updateTask(Long taskId, TaskDto taskDto) {
        logger.info("Updating task with ID: {}", taskId);
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new TodoApiException(HttpStatus.NOT_FOUND, "Task not found"));

        task.setTask(taskDto.getTask());
        task.setStatus(parseTaskStatus(taskDto.getStatus()));

        Task updatedTask = taskRepository.save(task);
        logger.info("Task updated successfully with ID: {}", updatedTask.getId());
        return updatedTask;
    }

    @Override
    public void deleteTask(Long taskId) {
        logger.info("Deleting task with ID: {}", taskId);
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new TodoApiException(HttpStatus.NOT_FOUND, "Task not found"));
        
        taskRepository.delete(task);
        logger.info("Task deleted successfully with ID: {}", taskId);
    }

    @Override
    public Task updateTaskStatus(Long taskId, String status) {
        logger.info("Updating status for task ID: {} to {}", taskId, status);
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new TodoApiException(HttpStatus.NOT_FOUND, "Task not found"));

        task.setStatus(parseTaskStatus(status));
        
        Task updatedTask = taskRepository.save(task);
        logger.info("Task status updated successfully for ID: {}", taskId);
        return updatedTask;
    }

    // Helper method to parse TaskStatus safely
    private TaskStatus parseTaskStatus(String status) {
        try {
            return TaskStatus.valueOf(status.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new TodoApiException(HttpStatus.BAD_REQUEST, "Invalid status. Allowed: PENDING, COMPLETED, ON_PROGRESS");
        }
    }
}
