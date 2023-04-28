package tech.iamalmir.restfulspring.service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import tech.iamalmir.restfulspring.model.Task;

public interface TaskService {
    Flux<Task> getAllTasks();

    // Create a new task
    Mono<Task> createTask(Task task);
}
