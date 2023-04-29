package tech.iamalmir.restfulspring.exceptions;

public class TaskNotFoundException extends RuntimeException {

    public TaskNotFoundException() {
        super("Task not found.");
    }
}