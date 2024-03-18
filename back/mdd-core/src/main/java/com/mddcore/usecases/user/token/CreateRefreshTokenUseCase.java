package com.mddcore.usecases.user.token;

import com.mddcore.domain.models.RefreshToken;
import com.mddcore.domain.models.User;
import com.mddcore.domain.repository.IRefreshTokenRepository;
import com.mddcore.domain.repository.IUserRepository;
import com.mddcore.usecases.UseCase;

import java.time.Instant;
import java.util.UUID;

/**
 * Use case for creating a new refresh token for a user.
 */
public class CreateRefreshTokenUseCase extends UseCase<CreateRefreshTokenUseCase.InputValues, CreateRefreshTokenUseCase.OutputValues> {
    private final IRefreshTokenRepository repository;
    private final IUserRepository userRepository;

    public CreateRefreshTokenUseCase(IRefreshTokenRepository repository, IUserRepository userRepository) {
        this.repository = repository;
        this.userRepository = userRepository;
    }

    /**
     * Generates and persists a new refresh token for the specified user, with a specified expiration time.
     * @param input the input values containing the user ID and the expiration time in milliseconds
     * @return the output values containing the generated refresh token
     * @throws IllegalArgumentException if the specified user does not exist
     */
    @Override
    public OutputValues execute(InputValues input) {
        User user = userRepository.findById(input.id).orElseThrow(() ->
                new IllegalArgumentException("User do not exist"));

        RefreshToken newRefreshToken = new RefreshToken(
                null,
                user,
                UUID.randomUUID().toString(),
                Instant.now().plusMillis(input.expirationsMs())
        );

        repository.save(newRefreshToken);
        return new OutputValues(newRefreshToken.getToken());
    }

    public record InputValues(Long id, Long expirationsMs) implements UseCase.InputValues {
    }

    public record OutputValues(String token) implements UseCase.OutputValues {
    }
}
