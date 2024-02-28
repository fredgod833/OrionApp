package com.mdddetails.repository.user;

import com.mdddetails.models.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserJpaRepository extends JpaRepository<UserEntity, Long> {
    Boolean existsByEmail(String email);
    Optional<UserEntity> findByEmail(String email);
}
