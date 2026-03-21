package com.newtome.newtomeapi.users.repository;

import com.newtome.newtomeapi.users.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findByUsernameIgnoreCase(String username);
    boolean existsByEmail(String email);
    boolean existsByUsername(String username);


    @Modifying
    @Query("update User u set u.lastLoginAt = :time where u.username = :username")
    int updateLastLoginAt(String username, LocalDateTime time);
}
