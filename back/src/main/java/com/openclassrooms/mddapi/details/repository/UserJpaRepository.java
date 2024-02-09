package com.openclassrooms.mddapi.details.repository;

import com.tonight.back.details.models.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserJpaRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByUsername(String username);
    boolean existsByUsername(String username);
    @Query("SELECT u.isAdmin FROM UserEntity u WHERE u.user_id = :id")
    Boolean isAdmin(@Param("id") Long id);
}
