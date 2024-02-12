package com.openclassrooms.mddapi.details.repository;

import com.openclassrooms.mddapi.details.models.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserJpaRepository extends JpaRepository<UserEntity, Long> {
    Boolean existsByEmail(String email);
}
