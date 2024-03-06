package com.mddinfrastructure.user;

import com.mddcore.domain.models.RefreshToken;
import com.mddcore.usecases.UseCaseExecutor;
import com.mddcore.usecases.auth.SignInRequest;
import com.mddcore.usecases.user.RegisterUseCase;
import com.mddcore.usecases.user.token.*;
import com.mddinfrastructure.mapper.UserUpdateMapper;
import com.mddinfrastructure.request.UserSettingRequest;
import com.mddinfrastructure.response.AuthResponse;
import com.mddinfrastructure.security.jwt.CookieJwt;
import com.mddinfrastructure.security.jwt.JwtTokenProvider;
import com.mddinfrastructure.security.userdetails.CustomUserDetails;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.CompletableFuture;

@Component
public class AuthController implements AuthResource {
    private final UseCaseExecutor useCaseExecutor;
    private final RegisterUseCase registerUseCase;
    private final AuthenticationManager authenticationManager;
    private final CreateRefreshTokenUseCase createRefreshTokenUseCase;
    private final DeleteRefreshTokenUseCase deleteRefreshTokenUseCase;
    private final FindByTokenUseCase findByTokenUseCase;
    private final UpdateRefreshTokenUseCase updateRefreshTokenUseCase;
    private final VerifyExpirationUseCase verifyExpirationUseCase;
    private final CookieJwt cookieJwt;
    private final JwtTokenProvider jwtTokenProvider;

    @Value("${mdd.app.jwtRefreshExpirationsMs}")
    private Long refreshTokenExpirationMs;

    public AuthController(UseCaseExecutor useCaseExecutor, RegisterUseCase registerUseCase, AuthenticationManager authenticationManager, CreateRefreshTokenUseCase createRefreshTokenUseCase, DeleteRefreshTokenUseCase deleteRefreshTokenUseCase, FindByTokenUseCase findByTokenUseCase, UpdateRefreshTokenUseCase updateRefreshTokenUseCase,
                          VerifyExpirationUseCase verifyExpirationUseCase, CookieJwt cookieJwt, JwtTokenProvider jwtTokenProvider) {
        this.useCaseExecutor = useCaseExecutor;
        this.registerUseCase = registerUseCase;
        this.authenticationManager = authenticationManager;
        this.createRefreshTokenUseCase = createRefreshTokenUseCase;
        this.deleteRefreshTokenUseCase = deleteRefreshTokenUseCase;
        this.findByTokenUseCase = findByTokenUseCase;
        this.updateRefreshTokenUseCase = updateRefreshTokenUseCase;
        this.verifyExpirationUseCase = verifyExpirationUseCase;
        this.cookieJwt = cookieJwt;
        this.jwtTokenProvider = jwtTokenProvider;
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
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(signInRequest.email(), signInRequest.password()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        ResponseCookie jwtCookie = cookieJwt.generateJwtCookie(userDetails);

        CompletableFuture<String> newRefreshToken = useCaseExecutor.execute(
                createRefreshTokenUseCase,
                new CreateRefreshTokenUseCase.InputValues(userDetails.getUser().getId(), refreshTokenExpirationMs),
                CreateRefreshTokenUseCase.OutputValues::token);

        String token = newRefreshToken.join();
        ResponseCookie jwtRefreshCookie = cookieJwt.generateRefreshJwtCookie(token);

        ResponseEntity<?> response = ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
                .header(HttpHeaders.SET_COOKIE, jwtRefreshCookie.toString())
                .body(new AuthResponse(userDetails.getUser().getId(),
                        userDetails.getPictureUrl()));

        return CompletableFuture.completedFuture(response);
    }

    @Override
    public CompletableFuture<ResponseEntity<?>> logoutUser() {
        Long authId = jwtTokenProvider.getAuthenticateUser();

        useCaseExecutor.execute(
                deleteRefreshTokenUseCase,
                new DeleteRefreshTokenUseCase.InputValues(authId),
                outputValues -> outputValues
        );

        ResponseCookie jwtCookie = cookieJwt.getCleanJwtCookie();
        ResponseCookie jwtRefreshCookie = cookieJwt.getCleanJwtRefreshCookie();

        ResponseEntity<?> response = ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
                .header(HttpHeaders.SET_COOKIE, jwtRefreshCookie.toString())
                .body("You've been signed out!");

        return CompletableFuture.completedFuture(response);
    }

    @Override
    public CompletableFuture<ResponseEntity<?>> refreshToken(HttpServletRequest request) {
        String token = cookieJwt.getJwtRefreshFromCookies(request);

        if ((token != null) && (token.length() > 0)) {
            CompletableFuture<RefreshToken> refreshToken = useCaseExecutor.execute(
                    findByTokenUseCase,
                    new FindByTokenUseCase.InputValues(token),
                    FindByTokenUseCase.OutputValues::refreshToken
            );

            RefreshToken refreshTokenInDb = refreshToken.join();

            CompletableFuture<RefreshToken> refreshTokenNotExpire = useCaseExecutor.execute(
                    verifyExpirationUseCase,
                    new VerifyExpirationUseCase.InputValues(refreshTokenInDb),
                    VerifyExpirationUseCase.OutputValues::token
            );

            RefreshToken tokenNotExpire = refreshTokenNotExpire.join();

            CompletableFuture<String> updatedRefreshToken =  useCaseExecutor.execute(
                    updateRefreshTokenUseCase,
                    new UpdateRefreshTokenUseCase.InputValues(tokenNotExpire, refreshTokenExpirationMs),
                    UpdateRefreshTokenUseCase.OutputValues::token
            );

            ResponseCookie jwtCookie = cookieJwt.generateJwtCookie(tokenNotExpire.getUser());
            ResponseCookie refreshJwtCookie = cookieJwt.generateRefreshJwtCookie(updatedRefreshToken.join());

            ResponseEntity<?> response = ResponseEntity.ok()
                    .header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
                    .header(HttpHeaders.SET_COOKIE, refreshJwtCookie.toString())
                    .body("Token is refreshed successfully!");

            return CompletableFuture.completedFuture(response);
        }
        return CompletableFuture.completedFuture(ResponseEntity.badRequest().body("Refresh token is empty"));
    }
}
