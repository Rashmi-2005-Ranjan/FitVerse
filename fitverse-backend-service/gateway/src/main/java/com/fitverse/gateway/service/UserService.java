package com.fitverse.gateway.service;

import com.fitverse.gateway.dto.RegisterRequest;
import com.fitverse.gateway.dto.UserResponse;
import com.fitverse.gateway.exception.BadRequestException;
import com.fitverse.gateway.exception.DownstreamServiceException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final WebClient userServiceWebClient;


    public Mono<Boolean> validateUser(String userId) {

        return userServiceWebClient
                .get ( )
                .uri ( "/api/users/validate-user/{userId}" , userId )
                .retrieve ( )
                .bodyToMono ( Boolean.class )
                .onErrorResume ( WebClientResponseException.class , ex -> {

                    if (ex.getStatusCode ( ) == HttpStatus.NOT_FOUND) {
                        return Mono.just ( false );
                    }

                    if (ex.getStatusCode ( ) == HttpStatus.BAD_REQUEST) {
                        return Mono.error (
                                new BadRequestException ( "Bad request to user service" )
                        );
                    }

                    return Mono.error (
                            new DownstreamServiceException (
                                    "Error occurred while communicating with user service"
                            )
                    );
                } );
    }

    public Mono<UserResponse> registerUser(RegisterRequest registerRequest) {
        return userServiceWebClient
                .post ( )
                .uri ( "/api/users/register" )
                .bodyValue ( registerRequest )
                .retrieve ( )
                .bodyToMono ( UserResponse.class )

                .onErrorResume ( WebClientResponseException.class , ex -> {
                    log.error (
                            "User Service registration failed. Status: {}, Response: {}" ,
                            ex.getStatusCode ( ) ,
                            ex.getResponseBodyAsString ( )
                    );

                    if (ex.getStatusCode ( ) == HttpStatus.BAD_REQUEST) {
                        return Mono.error (
                                new BadRequestException (
                                        "User registration failed: " +
                                                ex.getResponseBodyAsString ( )
                                )
                        );
                    }

                    return Mono.error (
                            new DownstreamServiceException (
                                    "Error occurred while communicating with user service"
                            )
                    );
                } );

    }
}
