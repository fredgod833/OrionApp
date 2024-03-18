package com.mddcore.usecases.user.token;

import com.mddcore.domain.models.RefreshToken;
import com.mddcore.domain.repository.IRefreshTokenRepository;
import com.mddcore.usecases.UseCase;

import java.time.Instant;

/**
 * Use case for verifying the expiration of a refresh token.
 */
public class VerifyTokenExpirationUseCase extends UseCase<VerifyTokenExpirationUseCase.InputValues, VerifyTokenExpirationUseCase.OutputValues> {

    private final IRefreshTokenRepository repository;

    public VerifyTokenExpirationUseCase(IRefreshTokenRepository repository) {
        this.repository = repository;
    }

    /**
     * Verifies the expiration of a refresh token.
     * @param input the input values containing the refresh token to verify
     * @return the output values containing the verified refresh token
     * @throws RuntimeException if the refresh token has expired
     */
    @Override
    public OutputValues execute(InputValues input) {
        if (input.token.getExpirationDate().compareTo(Instant.now()) < 0) {
            repository.delete(input.token);
            throw new RuntimeException("Refresh token was expired. Please make a new signIn request");
        }

        return new OutputValues(input.token);
    }

    public record InputValues(RefreshToken token) implements UseCase.InputValues {
    }

    public record OutputValues(RefreshToken token) implements UseCase.OutputValues {
    }
}
