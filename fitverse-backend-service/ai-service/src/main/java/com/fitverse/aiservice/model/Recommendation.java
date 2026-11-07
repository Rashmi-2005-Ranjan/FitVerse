package com.fitverse.aiservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "recommendations")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Recommendation {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String activityId;
    private String userId;
    @Enumerated(EnumType.STRING)
    private Activity.ActivityType activityType;
    @Column(columnDefinition = "LONGTEXT")
    private String recommendation;
    private List<String>improvements;
    private List<String>suggestions;
    private List<String> safety;
    @CreationTimestamp
    private LocalDateTime createdAt;
}