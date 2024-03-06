package com.mddcore.usecases.user.token;

import com.mddcore.domain.models.RefreshToken;
import com.mddcore.domain.repository.IRefreshTokenRepository;
import com.mddcore.usecases.UseCase;

import java.time.Instant;

public class VerifyExpirationUseCase extends UseCase<VerifyExpirationUseCase.InputValues, VerifyExpirationUseCase.OutputValues> {

    private final IRefreshTokenRepository repository;

    public VerifyExpirationUseCase(IRefreshTokenRepository repository) {
        this.repository = repository;
    }

    @Override
    public OutputValues execute(InputValues input) {
     if (input.token.getExpirationDate().compareTo(Instant.now()) < 0) {
         repository.delete(input.token);
         throw new RuntimeException("Refresh token was expired. Please make a new signin request");
     }

     return new OutputValues(input.token);
    }

    public record InputValues(RefreshToken token) implements UseCase.InputValues {}

    public record OutputValues(RefreshToken token) implements UseCase.OutputValues {}
}
