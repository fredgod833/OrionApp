package com.mddcore.usecases.user.token;

import com.mddcore.domain.models.RefreshToken;
import com.mddcore.domain.repository.IRefreshTokenRepository;
import com.mddcore.usecases.UseCase;

import java.time.Instant;
import java.util.UUID;

/**
 * Use case for updating a refresh token with a new token string and expiration date.
 */
public class UpdateRefreshTokenUseCase extends UseCase<UpdateRefreshTokenUseCase.InputValues, UpdateRefreshTokenUseCase.OutputValues> {

    private final IRefreshTokenRepository repository;

    public UpdateRefreshTokenUseCase(IRefreshTokenRepository repository) {
        this.repository = repository;
    }

    /**
     * Updates a refresh token with a new token string and expiration date.
     * @param input the input values containing the refresh token to update and the new expiration time in milliseconds
     * @return the output values containing the updated refresh token
     */
    @Override
    public OutputValues execute(InputValues input) {
        RefreshToken refreshToken = input.refreshToken();
        refreshToken.setToken(UUID.randomUUID().toString());
        refreshToken.setExpirationDate(Instant.now().plusMillis(input.expirationMs()));
        repository.save(refreshToken);
        return new OutputValues(refreshToken);
    }

    public record InputValues(RefreshToken refreshToken, Long expirationMs) implements UseCase.InputValues {
    }

    public record OutputValues(RefreshToken token) implements UseCase.OutputValues {
    }
}
