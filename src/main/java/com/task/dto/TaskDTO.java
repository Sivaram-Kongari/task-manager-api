package com.task.dto;

import lombok.Data;

@Data
public class TaskDTO {
	
	private String title;
	private String description;
	private String status; // PENDING or COMPLETED
}
