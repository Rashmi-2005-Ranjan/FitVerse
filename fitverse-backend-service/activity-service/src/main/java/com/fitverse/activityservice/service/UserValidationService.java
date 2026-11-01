package com.fitverse.activityservice.service;

import com.fitverse.activityservice.exception.BadRequestException;
import com.fitverse.activityservice.exception.DownstreamServiceException;
import com.fitverse.activityservice.exception.UserNotExistsException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserValidationService {
    private final WebClient userServiceWebClient;

    public Boolean validateUser(String userId) {
        log.info ( "Inside validateUser method in UserValidationService" );
        Boolean success = false;
        try {
            success = userServiceWebClient.get ( )
                    .uri ( "/api/users/validate-user/{userId}" , userId )
                    .retrieve ( )
                    .bodyToMono ( Boolean.class )
                    .block ( );
        } catch (WebClientResponseException e) {
            log.error ( "Error occurred while validating user: " + e.getMessage () );
            log.error ( "Error status code: " + e.getStatusCode () );
            success = false;
            if (e.getStatusCode ( ) == HttpStatus.NOT_FOUND) {
                throw new UserNotExistsException ( "User with given id doesn't exists" );
            } else if (e.getStatusCode ( ) == HttpStatus.BAD_REQUEST) {
                throw new BadRequestException ( "Invalid parameter type provided" );
            } else if (e.getStatusCode ( ) == HttpStatus.INTERNAL_SERVER_ERROR) {
                throw new DownstreamServiceException ( "Internal server error occurred while validating user" );
            }
        }
        return success;
    }
}
