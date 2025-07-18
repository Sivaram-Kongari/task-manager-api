package com.task.repository;

import com.task.model.Task;
import com.task.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {

	List<Task> findByUser(User user);
}
