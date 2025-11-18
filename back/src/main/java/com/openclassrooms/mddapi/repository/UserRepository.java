package com.openclassrooms.mddapi.repository;

import com.openclassrooms.mddapi.models.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repo Spring Data JPA pour les utilisateurs
 */
@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {

  Optional<UserEntity> findByLoginOrEmail(final String login, final String email);

  Boolean existsByLoginOrEmail(final String login, final String email);

}
