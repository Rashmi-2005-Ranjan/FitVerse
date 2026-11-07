package com.fitverse.aiservice.model;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Map;

@Data
public class Activity {
    private String id;
    private String userId;
    private ActivityType activityType;
    private Integer duration;
    private Integer caloriesBurned;
    private LocalDateTime startTime;
    private Map<String, Object> additionalMetrics;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String type;

    public enum ActivityType {
        RUNNING,
        CYCLING,
        SWIMMING,
        WALKING,
        WEIGHT_TRAINING,
        YOGA,
        CARDIO,
        STRETCHING,
        STRENGTH_TRAINING,
        OTHER
    }
}