package com.fitverse.aiservice.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "recommendations")
@Data
public class Recommendation {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String activityId;
    private String userId;
    private String activityType;
    private String recommendation;
    private List<String>improvement;
    private List<String>suggestions;
    private List<String> safety;
    @CreationTimestamp
    private LocalDateTime createdAt;
}