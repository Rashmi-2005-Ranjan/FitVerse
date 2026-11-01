package com.fitverse.aiservice.controller;

import com.fitverse.aiservice.model.Recommendation;
import com.fitverse.aiservice.service.RecommendationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/recommendations")
public class RecommendationController {
    private final RecommendationService recommendationService;

    @GetMapping("/users/{userId}")
    public ResponseEntity<List<Recommendation>> getUserRecommendationsOnUserId(@PathVariable String userId) {
        List<Recommendation> recommendations = recommendationService.getRecommendationsByUserId ( userId );
        return ResponseEntity.status ( HttpStatus.OK ).body ( recommendations );
    }

    @GetMapping("/user/activity/{activityId}")
    public ResponseEntity<Recommendation> getRecommendationBasedOnActivity(@PathVariable String activityId) {
        Recommendation recommendations = recommendationService.getRecommendationsByActivityId ( activityId );
        return ResponseEntity.status ( HttpStatus.OK ).body ( recommendations );
    }
}
