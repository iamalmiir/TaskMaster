package tech.iamalmir.restfulspring.service;

import org.springframework.stereotype.Service;

import java.util.UUID;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import tech.iamalmir.restfulspring.model.Task;
import tech.iamalmir.restfulspring.repository.TaskRepository;

@Service
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;

    public TaskServiceImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public Flux<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    @Override
    public Mono<Task> getTaskById(UUID id) {
        return taskRepository.findById(id);
    }

    @Override
    public Mono<Task> createTask(Task task) {
        Task newTask = Task.builder()
                .title(task.getTitle())
                .taskDescription(task.getTaskDescription())
                .build();

        return taskRepository.save(newTask);
    }

    @Override
    public Mono<Task> updateTask(UUID id, Task task) {
        return taskRepository.findById(id)
                .flatMap(existingTask -> {
                    existingTask.setTitle(task.getTitle() != null ? task.getTitle() : existingTask.getTitle());
                    existingTask.setTaskDescription(task.getTaskDescription() != null ? task.getTaskDescription() : existingTask.getTaskDescription());
                    existingTask.setCompleted(task.isCompleted() != existingTask.isCompleted() ? task.isCompleted() : existingTask.isCompleted());
                    return taskRepository.save(existingTask);
                });
    }


    @Override
    public Mono<Void> deleteTask(UUID id) {
        return taskRepository.deleteById(id);
    }

}
