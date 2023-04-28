package tech.iamalmir.restfulspring.exceptions;

import org.springframework.http.HttpStatus;

import java.util.Collections;
import java.util.List;

public class ErrorResponse {
    private final HttpStatus status;
    private final String message;
    private final List<String> errors;

    public ErrorResponse(HttpStatus status, String message, List<String> errors) {
        this.status = status;
        this.message = message;
        this.errors = errors;
    }

    public ErrorResponse(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
        this.errors = Collections.emptyList();
    }


    public HttpStatus getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public List<String> getErrors() {
        return errors;
    }
}
