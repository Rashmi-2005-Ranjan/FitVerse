package com.fitverse.aiservice.service;

import com.fitverse.aiservice.exception.RecommendationNotFoundException;
import com.fitverse.aiservice.model.Recommendation;
import com.fitverse.aiservice.repository.RecommendationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RecommendationService {
    private final RecommendationRepository recommendationRepository;

    public List<Recommendation> getRecommendationsByUserId(String userId) {
        return recommendationRepository.findByUserId ( userId );
    }

    public Recommendation getRecommendationsByActivityId(String activityId) {
        return recommendationRepository.findByActivityId ( activityId )
                .orElseThrow ( () -> new RecommendationNotFoundException ( "Recommendation not found for activityId: " + activityId ) );
    }
}
