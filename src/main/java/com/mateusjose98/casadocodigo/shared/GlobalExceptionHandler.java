package com.mateusjose98.casadocodigo.shared;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Set;
import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception, HttpServletRequest request) {
        log.error("Erro de validação: {}", exception.getMessage());
        Set<Object> response = exception.getBindingResult().getFieldErrors().stream().map(error -> {
            log.error("Campo: {}", error.getField());
            log.error("Mensagem: {}", error.getDefaultMessage());
            return String.format("Campo %s %s", error.getField(), error.getDefaultMessage());
        }).collect(Collectors.toSet());
        return ResponseEntity.badRequest().body(response);
    }

}
