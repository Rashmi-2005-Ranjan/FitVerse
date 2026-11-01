package com.fitverse.aiservice.service;

import com.fitverse.aiservice.model.Activity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ActivityMessageListenerService {

    @RabbitListener(queues = "${fitverse.rabbitmq.queue}")
    public void processActivity(Activity activity) {
        log.info ( "Received activity message: {}" , activity );
        log.info ( "Activity ID: {}" , activity.getId ( ) );
    }
}
