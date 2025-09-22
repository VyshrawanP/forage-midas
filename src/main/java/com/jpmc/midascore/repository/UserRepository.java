package com.jpmc.midascore.repository;

import com.jpmc.midascore.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUserId(String userId);  // finds a User by their userId
}
