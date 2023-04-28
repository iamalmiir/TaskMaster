package tech.iamalmir.restfulspring.service;

import java.util.UUID;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import tech.iamalmir.restfulspring.model.Task;

public interface TaskService {
    Flux<Task> getAllTasks();

    Mono<Task> getTaskById(UUID id);

    Mono<Task> createTask(Task task);
}