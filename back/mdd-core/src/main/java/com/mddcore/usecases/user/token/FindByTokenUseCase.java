package com.mddcore.usecases.user.token;

import com.mddcore.domain.models.RefreshToken;
import com.mddcore.domain.repository.IRefreshTokenRepository;
import com.mddcore.usecases.UseCase;

public class FindByTokenUseCase extends UseCase<FindByTokenUseCase.InputValues, FindByTokenUseCase.OutputValues> {

    private final IRefreshTokenRepository repository;

    public FindByTokenUseCase(IRefreshTokenRepository repository) {
        this.repository = repository;
    }

    @Override
    public OutputValues execute(InputValues input) {
        return new OutputValues(repository.findByToken(input.token()).orElse(null));
    }

    public record InputValues(String token) implements UseCase.InputValues {
    }

    public record OutputValues(RefreshToken refreshToken) implements UseCase.OutputValues {
    }
}
