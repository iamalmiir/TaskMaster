package tech.iamalmir.restfulspring.exceptions;

import java.util.UUID;

public class TaskNotFoundException extends RuntimeException {
    public TaskNotFoundException(UUID id) {
        super("Task not found - ID: " + id);
    }
}
