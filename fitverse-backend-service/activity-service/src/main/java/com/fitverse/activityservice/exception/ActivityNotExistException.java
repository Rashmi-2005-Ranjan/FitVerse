package com.fitverse.activityservice.exception;

public class ActivityNotExistException extends RuntimeException {
    public ActivityNotExistException(String message) {
        super ( message );
    }
}
