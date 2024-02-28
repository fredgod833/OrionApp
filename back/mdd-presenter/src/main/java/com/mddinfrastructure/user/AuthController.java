package com.mddinfrastructure.user;

import com.mddcore.usecases.UseCaseExecutor;
import com.mddcore.usecases.auth.SignInRequest;
import com.mddcore.usecases.auth.AuthResponse;
import com.mddcore.usecases.user.LoginUseCase;
import com.mddcore.usecases.user.RegisterUseCase;
import com.mddinfrastructure.mapper.UserUpdateMapper;
import com.mddinfrastructure.request.UserSettingRequest;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.concurrent.CompletableFuture;

@Component
public class AuthController implements AuthResource {
    private final UseCaseExecutor useCaseExecutor;
    private final RegisterUseCase registerUseCase;
    private final LoginUseCase loginUseCase;

    public AuthController(UseCaseExecutor useCaseExecutor, RegisterUseCase registerUseCase, LoginUseCase loginUseCase) {
        this.useCaseExecutor = useCaseExecutor;
        this.registerUseCase = registerUseCase;
        this.loginUseCase = loginUseCase;
    }

    @Override
    public CompletableFuture<SignInRequest> registerUser(@RequestBody UserSettingRequest userSettingRequest) {
        return useCaseExecutor.execute(
                registerUseCase,
                new RegisterUseCase.InputValues(UserUpdateMapper.INSTANCE.toDomain(userSettingRequest)),
                RegisterUseCase.OutputValues::signInRequest
        );
    }

    @Override
    @Transactional
    public CompletableFuture<AuthResponse> loginUser(@RequestBody SignInRequest signInRequest) {
        return useCaseExecutor.execute(
                loginUseCase,
                new LoginUseCase.InputValues(signInRequest),
                LoginUseCase.OutputValues::authResponse
        );
    }
}
