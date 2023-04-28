package tech.iamalmir.restfulspring.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import tech.iamalmir.restfulspring.model.Task;
import tech.iamalmir.restfulspring.service.TaskService;

@RestController
@RequestMapping(value = "/api/v1/tasks")
public class TaskController {
    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping(value = "/create")
    public Mono<Task> createTask(@Valid @RequestBody Task task) {
        return taskService.createTask(task);
    }

    /**
     * Get all tasks
     *
     * @return Flux<Task>
     */
    @GetMapping
    public Flux<Task> getAllTasks() {
        return taskService.getAllTasks();
    }

}
