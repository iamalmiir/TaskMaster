package tech.iamalmir.restfulspring.service;

import java.util.UUID;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import tech.iamalmir.restfulspring.models.Task;

public interface TaskService {
    Flux<Task> getAllTasks();

    Mono<Task> getTaskById(UUID id);

    Mono<Task> createTask(Task task);

    Mono<Task> updateTask(UUID id, Task task);

    Mono<Void> deleteTask(UUID id);
}
