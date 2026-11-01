package com.fitverse.userservice.repository;

import com.fitverse.userservice.model.User;
import org.jspecify.annotations.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,String> {
    Boolean existsByEmail(String email);
    Optional<User> findUserById(String id);
    Optional<Boolean> existsUserById(@NonNull String id);
}
