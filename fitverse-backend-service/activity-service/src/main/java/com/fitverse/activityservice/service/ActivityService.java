package com.fitverse.activityservice.service;

import com.fitverse.activityservice.dto.ActivityRequest;
import com.fitverse.activityservice.dto.ActivityResponse;
import com.fitverse.activityservice.model.Activity;
import com.fitverse.activityservice.repository.ActivityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ActivityService {
    private final ActivityRepository activityRepository;

    public ActivityResponse createActivity(ActivityRequest activityRequest) {
        return convertToResponse ( activityRepository.save ( convertToDocument ( activityRequest ) ) );
    }

    private ActivityResponse convertToResponse(Activity activity) {
        return ActivityResponse.builder ( )
                .id ( activity.getId ( ) )
                .userId ( activity.getUserId ( ) )
                .activityType ( activity.getActivityType ( ) )
                .startTime ( activity.getStartTime ( ) )
                .additionalMetrics ( activity.getAdditionalMetrics ( ) )
                .createdAt ( activity.getCreatedAt ( ) )
                .updatedAt ( activity.getUpdatedAt ( ) )
                .duration ( activity.getDuration ( ) )
                .caloriesBurned ( activity.getCaloriesBurned ( ) )
                .build ( );
    }

    private Activity convertToDocument(ActivityRequest request) {
        return Activity.builder ( )
                .userId ( request.getUserId ( ) )
                .activityType ( request.getActivityType ( ) )
                .startTime ( request.getStartTime ( ) )
                .additionalMetrics ( request.getAdditionalMetrics ( ) )
                .duration ( request.getDuration ( ) )
                .caloriesBurned ( request.getCaloriesBurned ( ) )
                .build ( );
    }
}
