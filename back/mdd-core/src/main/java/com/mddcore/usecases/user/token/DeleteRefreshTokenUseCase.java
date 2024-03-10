package com.mddcore.usecases.user.token;

import com.mddcore.domain.repository.IRefreshTokenRepository;
import com.mddcore.usecases.UseCase;

public class DeleteRefreshTokenUseCase extends UseCase<DeleteRefreshTokenUseCase.InputValues, DeleteRefreshTokenUseCase.OutputValues> {
    private final IRefreshTokenRepository repository;

    public DeleteRefreshTokenUseCase(IRefreshTokenRepository repository) {
        this.repository = repository;
    }

    @Override
    public OutputValues execute(InputValues input) {
        try {
            repository.deleteByUserId(input.userId());
            return new OutputValues();
        } catch (Exception e) {
            throw new IllegalArgumentException("Error while deleting token in db", e);
        }
    }

    public record InputValues(Long userId) implements UseCase.InputValues {
    }

    public record OutputValues() implements UseCase.OutputValues {
    }
}
