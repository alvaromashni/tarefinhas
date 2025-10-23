package dev.mashni.tarefinhas.controller;

import dev.mashni.tarefinhas.model.Task;
import dev.mashni.tarefinhas.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    private TaskService taskService;


    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping("/createtask")
    public ResponseEntity<Void> postBook(@RequestBody Task task){
        taskService.saveTask(task);
        System.out.println("Tarefinha salva com sucesso.");
        return ResponseEntity.ok().build();
    }

    @GetMapping("/getTask{id}")
    public ResponseEntity<Task> getTask(@RequestParam Long id){
        return ResponseEntity.ok(taskService.getTaskById(id));
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteTask(@RequestParam Long id){
        taskService.deleteTaskById(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/putTask")
    public ResponseEntity<Void> putTask(@RequestParam Long id, Task task){
        taskService.updateTask(id, task);
        return ResponseEntity.ok().build();
    }

}
