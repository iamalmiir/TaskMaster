package tech.iamalmir.restfulspring.service;

import org.springframework.stereotype.Service;

import java.util.UUID;

import jakarta.validation.Valid;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import tech.iamalmir.restfulspring.exceptions.TaskNotFoundException;
import tech.iamalmir.restfulspring.model.Task;
import tech.iamalmir.restfulspring.repository.TaskRepository;

@Service
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;

    public TaskServiceImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }


    /**
     * Get all tasks from database
     *
     * @return {@link Flux<Task>}
     */
    @Override
    public Flux<Task> getAllTasks() {
        return taskRepository.findAll();
    }


    /**
     * Get task by id from database
     *
     * @param id {@link UUID}
     * @return {@link Mono<Task>}
     */
    @Override
    public Mono<Task> getTaskById(UUID id) {
        return taskRepository.findById(id).switchIfEmpty(Mono.error(new TaskNotFoundException(id)));
    }

    /**
     * Create task in database
     *
     * @param task {@link Task}
     * @return {@link Mono<Task>}
     */
    @Override
    public Mono<Task> createTask(@Valid Task task) {
        Task newTask = Task.builder()
                .title(task.getTitle())
                .taskDescription(task.getTaskDescription())
                .build();

        return taskRepository.save(newTask);
    }

    /**
     * Update task in database
     * If task not found, return Mono Error with {@link TaskNotFoundException}
     * If task found, update task and return {@link Mono<Task>}
     * If task found, but no changes, return {@link Mono<Task>}
     *
     * @param id   {@link UUID}
     * @param task {@link Task}
     * @return {@link Mono<Task>}
     */
    @Override
    public Mono<Task> updateTask(UUID id, Task task) {
        return taskRepository.findById(id)
                .switchIfEmpty(Mono.error(new TaskNotFoundException(id)))
                .flatMap(existingTask -> {
                    existingTask.setTitle(task.getTitle() != null ? task.getTitle() : existingTask.getTitle());
                    existingTask.setTaskDescription(task.getTaskDescription() != null ? task.getTaskDescription() : existingTask.getTaskDescription());
                    existingTask.setCompleted(task.isCompleted() != existingTask.isCompleted() ? task.isCompleted() : existingTask.isCompleted());
                    return taskRepository.save(existingTask);
                });
    }

    /**
     * Delete task from database
     * If task not found, return Mono Error with {@link TaskNotFoundException}
     * If task found, delete task and return {@link Mono<Void>}
     *
     * @param id {@link UUID}
     * @return {@link Mono<Void>}
     */
    @Override
    public Mono<Void> deleteTask(UUID id) {
        // if task not found, return Mono.error
        return taskRepository.findById(id)
                .switchIfEmpty(Mono.error(new TaskNotFoundException(id)))
                .flatMap(taskRepository::delete);
    }

}
