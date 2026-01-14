package com.supermercado.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@ControllerAdvice
public class RestHandlerException extends ResponseEntityExceptionHandler {

    @ExceptionHandler(BadRequestException.class)
    protected ResponseEntity<Object> handleBadRequest(RuntimeException ex, WebRequest request){
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("Timestamp", LocalDateTime.now());
        body.put("Mesage", ex.getMessage());
        body.put("Error", HttpStatus.BAD_REQUEST.toString());

        return  new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler(NotFoundExceptionSup.class)
    protected ResponseEntity<Object> handleNotFound(RuntimeException ex, WebRequest request) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("Timestamp", LocalDateTime.now());
        body.put("Mesage", ex.getMessage());
        body.put("Error", HttpStatus.NOT_FOUND.toString());

        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }

        @ExceptionHandler(DataIntegrityViolationException.class)
        protected ResponseEntity<Object> handleViolation(RuntimeException ex, WebRequest request){
            Map<String, Object> body = new LinkedHashMap<>();
            body.put("Timestamp", LocalDateTime.now());
            body.put("Mesage", ex.getMessage());
            body.put("Error", HttpStatus.CONFLICT.value());

            return  new ResponseEntity<>(body, HttpStatus.CONFLICT);

    }
}
