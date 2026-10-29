package com.fitverse.activityservice.controller;

import com.fitverse.activityservice.dto.ActivityRequest;
import com.fitverse.activityservice.dto.ActivityResponse;
import com.fitverse.activityservice.service.ActivityService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/activities")
@RequiredArgsConstructor
public class ActivityController {
    private final ActivityService activityService;

    @PostMapping("/create-activity")
    public ResponseEntity<ActivityResponse>createActivity(@Valid @RequestBody ActivityRequest activityRequest) {
        ActivityResponse response = activityService.createActivity(activityRequest);
        return ResponseEntity.status ( HttpStatus.CREATED )
                .body ( response );
    }
}
