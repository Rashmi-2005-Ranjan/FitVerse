package com.fitverse.gateway.utils;

import com.fitverse.gateway.dto.RegisterRequest;
import com.fitverse.gateway.exception.RegisterRequestCreationException;
import com.fitverse.gateway.service.UserService;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.text.ParseException;
import java.util.Objects;

@Component
@Slf4j
@RequiredArgsConstructor
public class KeyCloakUserSyncFilter implements WebFilter {
    private final UserService userService;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange , WebFilterChain chain) {
        String token = exchange.getRequest ( )
                .getHeaders ( )
                .getFirst ( "Authorization" );

        String userId = exchange.getRequest ( )
                .getHeaders ( )
                .getFirst ( "X-User-Id" );


        RegisterRequest registerRequest = getUserDetails ( token );
        if(userId == null){
            userId = registerRequest.getKeycloakId ( );
        }

        if (userId != null) {
            String finalUserId = userId;
            return userService.validateUser ( userId )
                    .flatMap ( exists -> {
                        if (!exists) {
                            //!Register the user in user-service
                            log.info ( "User with id {} does not exist in user-service. Syncing..." , finalUserId );
                            if (Objects.nonNull ( registerRequest )) {
                                return userService.registerUser ( registerRequest )
                                        .then ( Mono.empty ( ) );
                            } else {
                                return Mono.empty ( );
                            }
                        } else {
                            log.info ( "User with id {} exists in user-service. Skipping Sync..." , finalUserId );
                            return Mono.empty ( );
                        }
                    } )
                    .then ( Mono.defer ( () -> {
                        ServerHttpRequest mutatedRequest = exchange.getRequest ( )
                                .mutate ( )
                                .header ( "X-User-Id" , finalUserId )
                                .build ( );
                        return chain.filter ( exchange.mutate ( ).request ( mutatedRequest ).build ( ) );
                    } ) );
        }
        return chain.filter ( exchange );
    }

    private RegisterRequest getUserDetails(String token) {
        try {
            String tokenWithoutBearer = token.replace ( "Bearer " , "" ).trim ( );
            SignedJWT signedJWT = SignedJWT.parse ( tokenWithoutBearer );
            JWTClaimsSet claimsSet = signedJWT.getJWTClaimsSet ( );
            return RegisterRequest.builder ( )
                    .email ( claimsSet.getStringClaim ( "email" ) )
                    .keycloakId ( claimsSet.getStringClaim ( "sub" ) )
                    .password ( "Pass@1234" )
                    .firstName ( claimsSet.getStringClaim ( "given_name" ) )
                    .lastName ( claimsSet.getStringClaim ( "family_name" ) )
                    .build ( );
        } catch (ParseException e) {
            log.error ( "Error occurred while fetching user details from Keycloak: {}" , e.getMessage ( ) );
            try {
                throw new RegisterRequestCreationException ( "Error occurred while creating register request from Keycloak token" );
            } catch (RegisterRequestCreationException ex) {
                throw new RuntimeException ( ex );
            }
        }
    }
}
