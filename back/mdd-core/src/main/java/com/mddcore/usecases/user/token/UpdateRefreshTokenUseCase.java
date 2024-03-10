package com.mddcore.usecases.user.token;

import com.mddcore.domain.models.RefreshToken;
import com.mddcore.domain.repository.IRefreshTokenRepository;
import com.mddcore.usecases.UseCase;

import java.time.Instant;
import java.util.UUID;

public class UpdateRefreshTokenUseCase extends UseCase<UpdateRefreshTokenUseCase.InputValues, UpdateRefreshTokenUseCase.OutputValues> {

    private final IRefreshTokenRepository repository;

    public UpdateRefreshTokenUseCase(IRefreshTokenRepository repository) {
        this.repository = repository;
    }

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
