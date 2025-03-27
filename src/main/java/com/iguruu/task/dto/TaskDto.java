package com.iguruu.task.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor


public class TaskDto {
 
	 private Long userId;
	 private String task;
	 private String status;
}
