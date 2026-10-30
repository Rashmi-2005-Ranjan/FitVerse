package com.fitverse.activityservice.repository;

import com.fitverse.activityservice.model.Activity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ActivityRepository extends MongoRepository<Activity, String> {
    List<Activity> findAllActivityByUserId(String userId);

    Optional<Activity> getActivityById(String id);
}
