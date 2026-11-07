package com.fitverse.aiservice.service;

import com.fitverse.aiservice.model.Activity;
import com.fitverse.aiservice.model.Recommendation;
import com.fitverse.aiservice.repository.RecommendationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ActivityMessageListenerService {
    private final ActivityAiService activityAiService;
    private final RecommendationRepository recommendationRepository;

    @RabbitListener(queues = "${fitverse.rabbitmq.queue}")
    public void processActivity(Activity activity) {
        log.info ( "Received activity message: {}" , activity );
        log.info ( "Activity ID: {}" , activity.getId ( ) );
        Recommendation recommendation = activityAiService.generateRecommendation ( activity );
        recommendationRepository.save ( recommendation );
    }
}
