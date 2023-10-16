package ru.test.distance.calculator.config;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.test.distance.calculator.exception.ErrorResponseDto;

import static java.util.stream.Collectors.toList;

@RestControllerAdvice
public class MyControllerAdvice {

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponseDto> handleConstraintViolationException(ConstraintViolationException e) {
        System.out.printf("ConstraintViolationException has throw, %s", e.getMessage());
        return ResponseEntity.badRequest().body(
                new ErrorResponseDto(
                        HttpStatus.BAD_REQUEST.name(),
                        "Validation exception",
                        e.getConstraintViolations().stream().map(s -> String.format("%s: %s", s.getPropertyPath().toString(), s.getMessage())).collect(toList())
                )
        );
    }

}
