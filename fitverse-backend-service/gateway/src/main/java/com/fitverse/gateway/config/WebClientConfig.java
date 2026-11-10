package com.fitverse.gateway.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Bean
    @LoadBalanced //Enable WebClient to resolve service names to actual instances using the eureka service registry
    public WebClient.Builder webClientBuilder() {
        return WebClient.builder ( );
    }

    @Bean

    public WebClient userServiceWebClient(WebClient.Builder webClientBuilder) {
        return webClientBuilder ( )
                .baseUrl ( "http://USER-SERVICE" ) // Set the base URL for the user service
                .build ( );
    }
}
