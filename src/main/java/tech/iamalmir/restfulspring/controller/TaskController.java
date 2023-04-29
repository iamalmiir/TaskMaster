package tech.iamalmir.restfulspring.controller;


import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import java.util.UUID;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;
import tech.iamalmir.restfulspring.exceptions.TaskNotFoundException;
import tech.iamalmir.restfulspring.model.Task;
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
        return ServerResponse.ok().body(taskService.getAllTasks(), Task.class);
    }

    /**
     * Get task by id
     *
     * @param req {@link ServerRequest}
     * @return {@link Mono<ServerResponse>}
     */
    public Mono<ServerResponse> getById(ServerRequest req) {
        return taskService.getTaskById(UUID.fromString(req.pathVariable("id")))
                .flatMap(task -> ServerResponse.ok().bodyValue(task))
                .switchIfEmpty(Mono.error(new TaskNotFoundException()));
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
                .flatMap(task -> ServerResponse.ok().bodyValue(task))
                .onErrorResume(err -> {
                    log.error("Error: {}", err.getMessage());
                    return ServerResponse.status(HttpStatus.INTERNAL_SERVER_ERROR)
                            .bodyValue("Something went wrong");
                });
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
                .flatMap(task -> ServerResponse.ok().bodyValue(task))
                .switchIfEmpty(ServerResponse.notFound().build())
                .onErrorResume(err -> {
                    log.error("Error: {}", err.getMessage());
                    if (err instanceof IllegalArgumentException) {
                        return ServerResponse.badRequest().bodyValue("Invalid task ID");
                    } else {
                        return ServerResponse.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                .bodyValue("Something went wrong");
                    }
                });
    }

    /**
     * Delete task
     *
     * @param req {@link ServerRequest}
     * @return {@link Mono<ServerResponse>}
     */
    public Mono<ServerResponse> delete(ServerRequest req) {
        return taskService.deleteTask(UUID.fromString(req.pathVariable("id")))
                .then(Mono.defer(() -> ServerResponse.ok().build()))
                .switchIfEmpty(ServerResponse.notFound().build())
                .onErrorResume(err -> {
                    log.error("Error: {}", err.getMessage());
                    if (err instanceof IllegalArgumentException) {
                        return ServerResponse.badRequest().bodyValue("Invalid task ID");
                    } else {
                        return ServerResponse.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                .bodyValue("Server error.");
                    }
                });
    }
}