package com.iguruu.task.service;

import com.iguruu.task.dto.TaskDto;
import com.iguruu.task.entity.Task;

import java.util.List;

public interface TaskService {
    Task assignTask(TaskDto taskDto);
    List<Task> getTasksByUserId(Long userId);
    Task updateTask(Long taskId, TaskDto taskDto);
    void deleteTask(Long taskId);
    Task updateTaskStatus(Long taskId, String status);
    List<Task> getAllTasks();
}