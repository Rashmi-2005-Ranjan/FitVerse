package com.fitverse.activityservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ActivityNotExistException.class)
    public ResponseEntity<String> handleActivityNotExistException(ActivityNotExistException ex) {
        return ResponseEntity.status ( HttpStatus.NOT_FOUND )
                .body ( ex.getMessage ( ) );
    }

}
