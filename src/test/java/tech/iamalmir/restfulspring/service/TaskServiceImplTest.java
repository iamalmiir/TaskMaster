package tech.iamalmir.restfulspring.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.List;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import tech.iamalmir.restfulspring.models.Task;
import tech.iamalmir.restfulspring.repository.TaskRepository;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class TaskServiceImplTest {
    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskServiceImpl taskService;

    @BeforeEach
    void setUp() {
        List<Task> tasks = List.of(
                Task.builder().title("Task 1").taskDescription("Task 1 Description").build(),
                Task.builder().title("Task 2").taskDescription("Task 2 Description").build(),
                Task.builder().title("Task 3").taskDescription("Task 3 Description").build()
        );

        when(taskRepository.findAll()).thenReturn(Flux.fromIterable(tasks));
        when(taskRepository.save(any(Task.class))).thenReturn(Mono.just(new Task()));
    }

    @Test
    void testGetAllTasks() {
        Flux<Task> tasks = taskService.getAllTasks();
        assertThat(tasks).isNotNull();
        assertThat(tasks.collectList().block()).isNotNull().isNotEmpty().hasSize(3)
                .extracting(Task::getTitle, Task::getTaskDescription)
                .contains(tuple("Task 1", "Task 1 Description"),
                        tuple("Task 2", "Task 2 Description"),
                        tuple("Task 3", "Task 3 Description"));
    }

    @Test
    void createTask() {
        Task task = Task.builder().title("Test").taskDescription("Test description").build();

        Mono<Task> savedTask = taskService.createTask(task);
        assertThat(savedTask)
                .isNotNull()
                .satisfies(currentTask -> {
                    assertThat(task.getTitle()).isEqualTo("Test");
                    assertThat(task.getTaskDescription()).isEqualTo("Test description");
                });
    }

}