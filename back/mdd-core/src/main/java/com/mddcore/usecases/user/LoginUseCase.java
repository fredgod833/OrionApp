package com.mddcore.usecases.user;


import com.mddcore.usecases.UseCase;
import com.mddcore.usecases.auth.IJwtExecFinal;
import com.mddcore.usecases.auth.SignInRequest;
import com.mddcore.usecases.auth.AuthResponse;

public class LoginUseCase extends UseCase<LoginUseCase.InputValues, LoginUseCase.OutputValues> {
    private final IJwtExecFinal jwtExecFinal;

    public LoginUseCase(IJwtExecFinal jwtExecFinal) {
        this.jwtExecFinal = jwtExecFinal;
    }

    @Override
    public OutputValues execute(InputValues input) {
        try {
            return new OutputValues(jwtExecFinal.jwtToken(input.signInRequest()));
        } catch (Exception e) {
            throw new IllegalStateException("An unexpected error occurred during login", e);
        }
    }

    public record InputValues(SignInRequest signInRequest) implements UseCase.InputValues {}

    public record OutputValues(AuthResponse authResponse) implements UseCase.OutputValues {}
}
