package dev.binarycoders.restpostgres;

import dev.binarycoders.restpostgres.exception.PersonNotFoundException;
import dev.binarycoders.restpostgres.exception.UpdateIdMatchException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(PersonNotFoundException.class)
    public ResponseEntity<ErrorResponse> handlePersonNotFound(final PersonNotFoundException ex) {
        final var error = new ErrorResponse(NOT_FOUND.value(), ex.getMessage());

        return new ResponseEntity<>(error, NOT_FOUND);
    }

    @ExceptionHandler(UpdateIdMatchException.class)
    public ResponseEntity<ErrorResponse> handlePersonNotFound(final UpdateIdMatchException ex) {
        final var error = new ErrorResponse(BAD_REQUEST.value(), ex.getMessage());

        return new ResponseEntity<>(error, BAD_REQUEST);
    }

    public record ErrorResponse(int status, String message) {
    }
}

