package tech.iamalmir.restfulspring.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

import tech.iamalmir.restfulspring.model.Task;

@Repository
public interface TaskRepository extends ReactiveCrudRepository<Task, UUID> {
}
