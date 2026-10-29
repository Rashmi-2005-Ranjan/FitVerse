package com.fitverse.userservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<String> handleUserAlreadyExistsException() {
        return ResponseEntity.status ( HttpStatus.CONFLICT )
                .body ( "User with this email already exists" );
    }

    @ExceptionHandler(UserNotExistsException.class)
    public ResponseEntity<String> handleUserNotExistsException() {
        return ResponseEntity.status ( HttpStatus.NOT_FOUND )
                .body ( "User with this id does not exist" );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        Map<String, String> errorMap = new HashMap<> ( );
        ex.getBindingResult ( )
                .getAllErrors ( )
                .forEach ( error -> {
                    String fieldName = ((FieldError) error).getField ( );
                    String errorMessage = error.getDefaultMessage ( );
                    errorMap.put ( "fieldName" , fieldName );
                    errorMap.put ( "errorMessage" , errorMessage );
                } );
        return ResponseEntity.status ( HttpStatus.BAD_REQUEST )
                .body ( errorMap );
    }
}
