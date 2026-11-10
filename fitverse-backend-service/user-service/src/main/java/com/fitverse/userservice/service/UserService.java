package com.fitverse.userservice.service;

import com.fitverse.userservice.dto.RegisterRequest;
import com.fitverse.userservice.dto.UserResponse;
import com.fitverse.userservice.exception.UserNotExistsException;
import com.fitverse.userservice.model.User;
import com.fitverse.userservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public UserResponse register(RegisterRequest request) {
        if (userRepository.existsByEmail ( request.getEmail ( ) )) {
            User existingUser = userRepository.findUserByEmail ( request.getEmail ( ) );
            return convertToResponse ( existingUser );
        }
        User user = convertToEntity ( request );
        user = userRepository.save ( user );
        return convertToResponse ( user );
    }


    private UserResponse convertToResponse(User userEntity) {
        return UserResponse.builder ( )
                .email ( userEntity.getEmail ( ) )
                .password ( userEntity.getPassword ( ) )
                .keycloakId ( userEntity.getKeycloakId ( ) )
                .firstName ( userEntity.getFirstName ( ) )
                .lastName ( userEntity.getLastName ( ) )
                .createdAt ( userEntity.getCreatedAt ( ) )
                .updatedAt ( userEntity.getUpdatedAt ( ) )
                .id ( userEntity.getId ( ) )
                .build ( );
    }

    private User convertToEntity(RegisterRequest registerRequest) {
        return User.builder ( )
                .email ( registerRequest.getEmail ( ) )
                .password ( registerRequest.getPassword ( ) )
                .keycloakId ( registerRequest.getKeycloakId ( ) )
                .firstName ( registerRequest.getFirstName ( ) )
                .lastName ( registerRequest.getLastName ( ) )
                .build ( );
    }

    public UserResponse getUserProfileByUserId(String userId) {
        User user = userRepository.findUserById ( userId )
                .orElseThrow ( () -> new UserNotExistsException ( "User with given id doesn't exists" ) );
        return convertToResponse ( user );
    }

    public Boolean isUserExist(String userId) {
        Boolean isExists = userRepository.existsUserByKeycloakId ( userId );
        if (!isExists) {
            throw new UserNotExistsException ( "User with given id doesn't exists" );
        }
        return true;
    }
}