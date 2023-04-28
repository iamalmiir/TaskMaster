package tech.iamalmir.restfulspring.exceptions;

import org.springframework.validation.BindingResult;

import java.io.Serial;
import java.io.Serializable;

public class InvalidRequestException extends RuntimeException implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private final transient BindingResult result;

    public InvalidRequestException(BindingResult result) {
        this.result = result;
    }

    public BindingResult getResult() {
        return result;
    }
}
