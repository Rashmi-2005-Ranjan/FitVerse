package com.fitverse.gateway.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserNotExistsException.class)
    public ResponseEntity<String> handleUserNotExistsException() {
        return ResponseEntity.status ( HttpStatus.NOT_FOUND )
                .body ( "User with this id does not exist" );
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<String> handleBadRequestException() {
        return ResponseEntity.status ( HttpStatus.BAD_REQUEST )
                .body ( "The request could not be processed due to invalid or missing data." );
    }

    @ExceptionHandler(RegisterRequestCreationException.class)
    public ResponseEntity<String> handleRegisterRequestCreationException(RegisterRequestCreationException ex) {
        return ResponseEntity.status ( HttpStatus.INTERNAL_SERVER_ERROR )
                .body ( "Error occurred while creating register request: " + ex.getMessage ( ) );
    }

    @ExceptionHandler(DownstreamServiceException.class)
    public ResponseEntity<Map<String, Object>> handleDownstreamServiceException(DownstreamServiceException ex) {
        Map<String, Object> response = new LinkedHashMap<> ( );
        response.put ( "timestamp" , LocalDateTime.now ( ) );
        response.put ( "status" , HttpStatus.BAD_GATEWAY.value ( ) ); // 502 is semantically perfect here
        response.put ( "error" , "Bad Gateway" );
        response.put ( "message" , "The identity verification service is temporarily unavailable. Please try again later." );
        return ResponseEntity
                .status ( HttpStatus.BAD_GATEWAY )
                .body ( response );
    }

}
