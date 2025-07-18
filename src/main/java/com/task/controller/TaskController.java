package com.task.controller;

import com.task.dto.TaskDTO;
import com.task.model.Task;
import com.task.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
public class TaskController {

	private final TaskService taskService;

	@PostMapping
	public Task createTask(@RequestBody TaskDTO taskDTO, Authentication auth) {
		return taskService.createTask(auth.getName(), taskDTO);
	}

	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/admin/users")
	public List<Task> getAllTasks(Authentication auth) {
		return taskService.getAllTasks(auth.getName());
	}

	@GetMapping("/{id}")
	public Task getTask(@PathVariable Long id, Authentication auth) {
		return taskService.getTaskById(auth.getName(), id);
	}

	@PutMapping("/{id}")
	public Task updateTask(@PathVariable Long id, @RequestBody TaskDTO taskDTO, Authentication auth) {
		return taskService.updateTask(auth.getName(), id, taskDTO);
	}

	@DeleteMapping("/{id}")
	public String deleteTask(@PathVariable Long id, Authentication auth) {
		taskService.deleteTask(auth.getName(), id);
		return "Task deleted";
	}
}
