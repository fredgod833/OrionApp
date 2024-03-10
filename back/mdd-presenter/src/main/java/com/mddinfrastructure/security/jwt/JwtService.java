package com.mddinfrastructure.security.jwt;

import com.mddcore.domain.models.RefreshToken;
import com.mddcore.usecases.UseCaseExecutor;
import com.mddcore.usecases.user.token.*;
import com.mddinfrastructure.response.AuthResponse;
import com.mddinfrastructure.security.userdetails.CustomUserDetails;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class JwtService {
    private final CookieJwt cookieJwt;
    private final JwtTokenProvider jwtTokenProvider;
    private final CreateRefreshTokenUseCase createRefreshTokenUseCase;
    private final UseCaseExecutor useCaseExecutor;
    private final FindByTokenUseCase findByTokenUseCase;
    private final VerifyExpirationUseCase verifyExpirationUseCase;
    private final UpdateRefreshTokenUseCase updateRefreshTokenUseCase;
    private final DeleteRefreshTokenUseCase deleteRefreshTokenUseCase;

    @Value("${mdd.app.jwtRefreshExpirationsMs}")
    private Long refreshTokenExpirationMs;

    public JwtService(CookieJwt cookieJwt, JwtTokenProvider jwtTokenProvider, CreateRefreshTokenUseCase createRefreshTokenUseCase, UseCaseExecutor useCaseExecutor, FindByTokenUseCase findByTokenUseCase, VerifyExpirationUseCase verifyExpirationUseCase,
                      UpdateRefreshTokenUseCase updateRefreshTokenUseCase, DeleteRefreshTokenUseCase deleteRefreshTokenUseCase) {
        this.cookieJwt = cookieJwt;
        this.jwtTokenProvider = jwtTokenProvider;
        this.createRefreshTokenUseCase = createRefreshTokenUseCase;
        this.useCaseExecutor = useCaseExecutor;
        this.findByTokenUseCase = findByTokenUseCase;
        this.verifyExpirationUseCase = verifyExpirationUseCase;
        this.updateRefreshTokenUseCase = updateRefreshTokenUseCase;
        this.deleteRefreshTokenUseCase = deleteRefreshTokenUseCase;
    }

    public CompletableFuture<ResponseEntity<?>> generateAuthResponse(CustomUserDetails userDetails, ResponseCookie jwtCookie) {
        return useCaseExecutor.execute(
                createRefreshTokenUseCase,
                new CreateRefreshTokenUseCase.InputValues(userDetails.getUser().getId(), refreshTokenExpirationMs),
                outputValues -> {
                    ResponseCookie cookie = cookieJwt.generateJwtCookie(userDetails);
                    ResponseCookie jwtRefresh = cookieJwt.generateRefreshJwtCookie(outputValues.token());

                    return ResponseEntity.ok()
                            .header(HttpHeaders.SET_COOKIE, cookie.toString())
                            .header(HttpHeaders.SET_COOKIE, jwtRefresh.toString())
                            .body(new AuthResponse(userDetails.getUser().getId(), userDetails.getPictureUrl()));
                }
        );
    }

    public CompletableFuture<ResponseEntity<?>> generateLogoutResponse() {
        Long authId = jwtTokenProvider.getAuthenticateUser();
        return useCaseExecutor.execute(
                deleteRefreshTokenUseCase,
                new DeleteRefreshTokenUseCase.InputValues(authId),
                output -> {
                    ResponseCookie jwtCookie = cookieJwt.getCleanJwtCookie();
                    ResponseCookie jwtRefreshCookie = cookieJwt.getCleanJwtRefreshCookie();

                    return ResponseEntity.ok()
                            .header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
                            .header(HttpHeaders.SET_COOKIE, jwtRefreshCookie.toString())
                            .body("You've been signed out successfully!");
                }
        );
    }

    public CompletableFuture<ResponseEntity<?>> generateRefreshTokenResponse(String token) {
        if ((token == null) || (token.isEmpty())) {
            return CompletableFuture.completedFuture(ResponseEntity.badRequest().body("Refresh token is empty"));
        }

        return getRefreshToken(token)
                .thenCompose(this::verifyTokenNotExpire)
                .thenCompose(this::updateRefreshToken)
                .thenApply(validToken -> {
                    ResponseCookie cookie = cookieJwt.generateJwtCookie(validToken.getUser());
                    ResponseCookie jwtRefresh = cookieJwt.generateRefreshJwtCookie(validToken.getToken());
                    return ResponseEntity.ok()
                            .header(HttpHeaders.SET_COOKIE, cookie.toString())
                            .header(HttpHeaders.SET_COOKIE, jwtRefresh.toString())
                            .body("Token is refreshed successfully!");
                });
    }

    private CompletableFuture<RefreshToken> getRefreshToken(String token) {
        return useCaseExecutor.execute(
                findByTokenUseCase,
                new FindByTokenUseCase.InputValues(token),
                FindByTokenUseCase.OutputValues::refreshToken
        );
    }

    private CompletableFuture<RefreshToken> verifyTokenNotExpire(RefreshToken token) {
        return useCaseExecutor.execute(
                verifyExpirationUseCase,
                new VerifyExpirationUseCase.InputValues(token),
                VerifyExpirationUseCase.OutputValues::token);
    }

    private CompletableFuture<RefreshToken> updateRefreshToken(RefreshToken token) {
        return useCaseExecutor.execute(
                updateRefreshTokenUseCase,
                new UpdateRefreshTokenUseCase.InputValues(token, refreshTokenExpirationMs),
                UpdateRefreshTokenUseCase.OutputValues::token);
    }
}
