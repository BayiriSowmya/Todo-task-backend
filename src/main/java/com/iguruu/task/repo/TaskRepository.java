package com.iguruu.task.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.iguruu.task.entity.Task;

public interface TaskRepository extends JpaRepository<Task, Long> {
	

	List<Task> findByUserId(Long userId);
}
