package com.mddcore.usecases.user;

import com.mddcore.domain.repository.IRefreshTokenRepository;
import com.mddcore.usecases.UseCase;

/**
 * Use case for logging out a user by deleting their refresh token.
 */
public class LogoutUserUseCase extends UseCase<LogoutUserUseCase.InputValues, LogoutUserUseCase.OutputValues> {
    private final IRefreshTokenRepository refreshToken;


    public LogoutUserUseCase(IRefreshTokenRepository refreshToken) {
        this.refreshToken = refreshToken;
    }

    /**
     * Logs out a user by deleting their refresh token.
     * @param input the input values containing the user ID
     * @return the output values indicating whether the logout operation was successful
     * @throws IllegalArgumentException if the refresh token for the user cannot be found
     */
    @Override
    public OutputValues execute(InputValues input) {
        try {
            refreshToken.deleteByUserId(input.user_id());
            return new OutputValues(true);
        } catch (Exception e) {
            throw new IllegalArgumentException("Cant find refresh token for this user, cant delete token");
        }
    }

    public record InputValues(Long user_id) implements UseCase.InputValues {
    }

    public record OutputValues(Boolean isDeleted) implements UseCase.OutputValues {
    }
}
