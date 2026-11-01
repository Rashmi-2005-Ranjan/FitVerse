package com.fitverse.aiservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RecommendationNotFoundException.class)
    public ResponseEntity<String> handleRecommendationNotFoundException(RecommendationNotFoundException ex) {
        return ResponseEntity
                .status ( HttpStatus.NOT_FOUND )
                .body ( "Recommendation not found" );
    }
}
