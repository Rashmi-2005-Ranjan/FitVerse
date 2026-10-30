package com.fitverse.activityservice.controller;

import com.fitverse.activityservice.dto.ActivityRequest;
import com.fitverse.activityservice.dto.ActivityResponse;
import com.fitverse.activityservice.service.ActivityService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/activities")
@RequiredArgsConstructor
public class ActivityController {
    private final ActivityService activityService;

    @PostMapping("/create-activity")
    public ResponseEntity<ActivityResponse> createActivity(@Valid @RequestBody ActivityRequest activityRequest) {
        ActivityResponse response = activityService.createActivity ( activityRequest );
        return ResponseEntity.status ( HttpStatus.CREATED )
                .body ( response );
    }

    @GetMapping("/get-activity/{userId}")
    public ResponseEntity<List<ActivityResponse>> getActivityOfSpecificUser(@PathVariable String userId) {
        List<ActivityResponse> response = activityService.getAllActivityOfAUser ( userId );
        return ResponseEntity.status ( HttpStatus.OK )
                .body ( response );
    }

    @GetMapping("/get-single-activity/{activityId}")
    public ResponseEntity<ActivityResponse> getSingleActivity(@PathVariable String activityId) {
        ActivityResponse response = activityService.getActivityByActivityId ( activityId );
        return ResponseEntity.status ( HttpStatus.OK )
                .body ( response );
    }
}
