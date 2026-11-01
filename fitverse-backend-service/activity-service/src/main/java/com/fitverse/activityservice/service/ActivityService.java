package com.fitverse.activityservice.service;

import com.fitverse.activityservice.dto.ActivityRequest;
import com.fitverse.activityservice.dto.ActivityResponse;
import com.fitverse.activityservice.exception.ActivityNotExistException;
import com.fitverse.activityservice.exception.UserNotExistsException;
import com.fitverse.activityservice.model.Activity;
import com.fitverse.activityservice.repository.ActivityRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ActivityService {
    private final ActivityRepository activityRepository;
    private final UserValidationService userValidationService;
    private final RabbitTemplate rabbitTemplate;

    @Value ( "${fitverse.rabbitmq.exchange}" )
    private String exchange;
    @Value ( "${fitverse.rabbitmq.routing-key}" )
    private String routingKey;

    public ActivityResponse createActivity(ActivityRequest activityRequest) {
        Activity activity = convertToDocument ( activityRequest );
        Activity savedActivity = activityRepository.save ( activity );
        //Publishing the activity to RabbitMQ exchange for further processing by ai service
        try{
            rabbitTemplate.convertAndSend(exchange, routingKey, savedActivity);
        }catch (Exception e){
            log.error("Error occurred while publishing activity to RabbitMQ: ", e);
        }
        return convertToResponse ( savedActivity );
    }

    public List<ActivityResponse> getAllActivityOfAUser(String userId) {
        return activityRepository.findAllActivityByUserId ( userId )
                .stream ( )
                .map ( this::convertToResponse )
                .toList ( );
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
        if (!userValidationService.validateUser ( request.getUserId ( ) )) {
            throw new UserNotExistsException ( "Invalid user id for " + request.getActivityType ( ) + " activity..." );
        } else {
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

    public ActivityResponse getActivityByActivityId(String activityId) {
        Activity activity = activityRepository.getActivityById ( activityId )
                .orElseThrow ( () -> new ActivityNotExistException ( "Activity with id: " + activityId + " does not exist" ) );
        return convertToResponse ( activity );
    }
}
