package com.fitverse.userservice.controllers;

import com.fitverse.userservice.dto.RegisterRequest;
import com.fitverse.userservice.dto.UserResponse;
import com.fitverse.userservice.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<UserResponse> registerUser(@Valid @RequestBody RegisterRequest request) {
        UserResponse response = userService.register ( request );
        return ResponseEntity.status ( HttpStatus.CREATED )
                .body ( response );
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserResponse> getUserProfile(@Valid @PathVariable String userId) {
        UserResponse response = userService.getUserProfileByUserId ( userId );
        return ResponseEntity.status ( HttpStatus.OK )
                .body ( response );
    }
}
