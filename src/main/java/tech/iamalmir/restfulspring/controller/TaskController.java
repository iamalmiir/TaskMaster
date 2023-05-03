package tech.iamalmir.restfulspring.controller;


import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import java.util.UUID;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;
import tech.iamalmir.restfulspring.models.Task;
import tech.iamalmir.restfulspring.service.TaskService;

@Slf4j
@Component
public class TaskController {
    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    /**
     * Get all tasks
     *
     * @param req {@link ServerRequest}
     * @return {@link Mono<ServerResponse>}
     */
    public Mono<ServerResponse> all(ServerRequest req) {
        return taskService.getAllTasks()
                .collectList()
                .flatMap(tasks -> ServerResponse.ok().bodyValue(tasks));
    }


    /**
     * Get task by id
     *
     * @param req {@link ServerRequest}
     * @return {@link Mono<ServerResponse>}
     */
    public Mono<ServerResponse> getById(ServerRequest req) {
        return taskService.getTaskById(UUID.fromString(req.pathVariable("id")))
                .flatMap(task -> ServerResponse.ok().bodyValue(task));
    }

    /**
     * Create task
     *
     * @param req {@link ServerRequest}
     * @return {@link Mono<ServerResponse>}
     */
    public Mono<ServerResponse> create(ServerRequest req) {
        return req.bodyToMono(Task.class)
                .flatMap(taskService::createTask)
                .flatMap(task -> ServerResponse.ok().bodyValue(task));
    }

    /**
     * Update task
     *
     * @param req {@link ServerRequest}
     * @return {@link Mono<ServerResponse>}
     */
    public Mono<ServerResponse> update(ServerRequest req) {
        return req.bodyToMono(Task.class)
                .flatMap(task -> taskService.updateTask(UUID.fromString(req.pathVariable("id")), task))
                .flatMap(task -> ServerResponse.ok().bodyValue(task));
    }

    /**
     * Delete task
     *
     * @param req {@link ServerRequest}
     * @return {@link Mono<ServerResponse>}
     */
    public Mono<ServerResponse> delete(ServerRequest req) {
        return taskService.deleteTask(UUID.fromString(req.pathVariable("id")))
                .then(Mono.defer(() -> ServerResponse.ok().build()));
    }
}