package dev.mashni.tarefinhas.repository;

import dev.mashni.tarefinhas.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task, Long> {
}
