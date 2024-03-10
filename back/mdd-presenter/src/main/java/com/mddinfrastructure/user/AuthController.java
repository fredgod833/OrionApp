package com.mddinfrastructure.user;

import com.mddcore.usecases.UseCaseExecutor;
import com.mddcore.usecases.auth.SignInRequest;
import com.mddcore.usecases.user.RegisterUseCase;
import com.mddinfrastructure.mapper.UserUpdateMapper;
import com.mddinfrastructure.request.UserSettingRequest;
import com.mddinfrastructure.security.jwt.CookieJwt;
import com.mddinfrastructure.security.jwt.JwtService;
import com.mddinfrastructure.security.usecases.AuthenticateUserUseCase;
import com.mddinfrastructure.security.userdetails.CustomUserDetails;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.CompletableFuture;

@Component
public class AuthController implements AuthResource {
    private final UseCaseExecutor useCaseExecutor;
    private final RegisterUseCase registerUseCase;
    private final AuthenticateUserUseCase authenticateUserUseCase;
    private final CookieJwt cookieJwt;
    private final JwtService jwtService;

    public AuthController(UseCaseExecutor useCaseExecutor, RegisterUseCase registerUseCase,
                          AuthenticateUserUseCase authenticateUserUseCase, CookieJwt cookieJwt, JwtService jwtService) {
        this.useCaseExecutor = useCaseExecutor;
        this.registerUseCase = registerUseCase;
        this.authenticateUserUseCase = authenticateUserUseCase;
        this.cookieJwt = cookieJwt;
        this.jwtService = jwtService;
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
    public CompletableFuture<ResponseEntity<?>> loginUser(@RequestBody SignInRequest signInRequest) {
        CompletableFuture<AuthenticateUserUseCase.OutputValues> authenticateUser = useCaseExecutor.execute(
                authenticateUserUseCase,
                new AuthenticateUserUseCase.InputValues(signInRequest),
                outputValues -> outputValues
        );

        CustomUserDetails userDetails = authenticateUser.join().userDetails();
        ResponseCookie jwtCookie = authenticateUser.join().jwtCookie();

        return jwtService.generateAuthResponse(userDetails, jwtCookie);
    }

    @Override
    public CompletableFuture<ResponseEntity<?>> logoutUser() {
        return jwtService.generateLogoutResponse();
    }

    @Override
    public CompletableFuture<ResponseEntity<?>> refreshToken(HttpServletRequest request) {
        String token = cookieJwt.getJwtRefreshFromCookies(request);
        return jwtService.generateRefreshTokenResponse(token);
    }
}

