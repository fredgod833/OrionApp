package com.openclassrooms.mddapi.repository;

import com.openclassrooms.mddapi.models.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

  Optional<UserEntity> findByLoginOrEmail(final String login, final String email);

  Boolean existsByLoginOrEmail(final String login, final String email);


}
