package com.fitverse.userservice.repository;

import com.fitverse.userservice.model.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.jspecify.annotations.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,String> {
    Boolean existsByEmail(String email);
    Optional<User> findUserById(String id);
    Boolean existsUserByKeycloakId(String keycloakId);
    User findUserByEmail(String email);
}
