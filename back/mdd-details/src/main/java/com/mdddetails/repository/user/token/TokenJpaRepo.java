package com.mdddetails.repository.user.token;

import com.mdddetails.models.RefreshTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface TokenJpaRepo extends JpaRepository<RefreshTokenEntity, Long> {
    Optional<RefreshTokenEntity> findByToken(String token);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM refreshtoken WHERE user_id=1", nativeQuery = true)
    void deleteByUserId(Long id);
}
