package com.task.service;

import com.task.dto.TaskDTO;
import com.task.model.Role;
import com.task.model.Task;
import com.task.model.User;
import com.task.repository.TaskRepository;
import com.task.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TaskService {

	private final TaskRepository taskRepository;
	private final UserRepository userRepository;

	public Task createTask(String username, TaskDTO taskDTO) {

		User user = userRepository.findByUsername(username)
				.orElseThrow(() -> new RuntimeException("User not found"));

		Task task = Task.builder()
				.title(taskDTO.getTitle())
				.description(taskDTO.getDescription())
				.status(taskDTO.getStatus())
				.createdAt(LocalDateTime.now())
				.user(user)
				.build();

		return taskRepository.save(task);
	}

	public List<Task> getAllTasks(String username) {
		User user = userRepository.findByUsername(username)
				.orElseThrow(() -> new RuntimeException("User not found"));

		if (user.getRole() == Role.ADMIN) {
			return taskRepository.findAll();
		}
		return taskRepository.findByUser(user);
	}


	public Task getTaskById(String username, Long id) {

		Task task = taskRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Task not found"));

		if (!task.getUser().getUsername().equals(username)) {
			throw new RuntimeException("Unauthorized access");
		}

		return task;
	}

	public Task updateTask(String username, Long id, TaskDTO taskDTO) {

		Task task = getTaskById(username, id);
		task.setTitle(taskDTO.getTitle());
		task.setDescription(taskDTO.getDescription());
		task.setStatus(taskDTO.getStatus());
		return taskRepository.save(task);
	}

	public void deleteTask(String username, Long id) {

		Task task = getTaskById(username, id);
		taskRepository.delete(task);
	}
}
