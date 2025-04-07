package com.example.demo.exceptions.handlers;

import java.util.Collections;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.example.demo.dto.response.Error;
import com.example.demo.dto.response.GenericResponse;
import com.example.demo.exceptions.ResourceNotFoundException;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({ResourceNotFoundException.class})
    protected ResponseEntity<GenericResponse> handleResourceNotFoundException(
        ResourceNotFoundException ex, WebRequest request) {
        var error = new Error(ex.getErrorCode(), ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                             .body(GenericResponse.builder()
                                                  .status(HttpStatus.NOT_FOUND.value())
                                                  .errors(Collections.singleton(error))
                                                  .build());
    }

    @Override
    protected  ResponseEntity<Object> handleMethodArgumentNotValid(
        MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        var errors = ex.getBindingResult().getAllErrors()
            .stream()
            .filter(e -> e instanceof FieldError)
            .map(e -> (FieldError) e)
            .map(fe -> new Error(fe.getDefaultMessage(), fe.getField()))
            .toList();

        return ResponseEntity.badRequest()
            .body(GenericResponse.builder()
                      .status(HttpStatus.BAD_REQUEST.value())
                      .message("Invalid request!")
                      .errors(errors)
                      .build());
    }
}
