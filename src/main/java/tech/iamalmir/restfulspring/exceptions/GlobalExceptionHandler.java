package tech.iamalmir.restfulspring.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InvalidRequestException.class)
    public ResponseEntity<ErrorResponse> handleInvalidRequestException(InvalidRequestException ex) {
        BindingResult result = ex.getResult();
        List<String> errors = new ArrayList<>();
        for (FieldError error : result.getFieldErrors()) {
            errors.add(error.getDefaultMessage());
        }
        ErrorResponse response = new ErrorResponse(HttpStatus.BAD_REQUEST, "Validation failed: " + errors.get(0), errors);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}