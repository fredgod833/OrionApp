package com.mddcore.usecases.user.token;

import com.mddcore.domain.models.RefreshToken;
import com.mddcore.domain.repository.IRefreshTokenRepository;
import com.mddcore.usecases.UseCase;

/**
 * Use case for finding a refresh token by its token string.
 */
public class FindByTokenUseCase extends UseCase<FindByTokenUseCase.InputValues, FindByTokenUseCase.OutputValues> {

    private final IRefreshTokenRepository repository;

    public FindByTokenUseCase(IRefreshTokenRepository repository) {
        this.repository = repository;
    }

    /**
     * Finds a refresh token by its token string.
     * @param input the input values containing the token string to search for
     * @return the output values containing the found refresh token (or null if not found)
     */
    @Override
    public OutputValues execute(InputValues input) {
        return new OutputValues(repository.findByToken(input.token()).orElse(null));
    }

    public record InputValues(String token) implements UseCase.InputValues {
    }

    public record OutputValues(RefreshToken refreshToken) implements UseCase.OutputValues {
    }
}
