package dev.mashni.tarefinhas.service;

import dev.mashni.tarefinhas.model.Task;
import dev.mashni.tarefinhas.repository.TaskRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class TaskService {

    private final TaskRepository taskRepository;


    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public void saveTask(Task task){
        taskRepository.saveAndFlush(task);
    }

    public Task getTaskById(Long id){
        return (Task) taskRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Task não encontrada")
        );
    }

    @Transactional
    public void deleteTaskById(Long id){
        taskRepository.deleteById(id);
    }

    public void updateTask(Long id, Task task){
        Task taskEntity = getTaskById(id);
        taskEntity.setTaskTitle(task.getTaskTitle());
        taskEntity.setTaskDescription(task.getTaskDescription());

        taskRepository.saveAndFlush(taskEntity);
    }

}
