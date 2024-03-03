package com.mddcore.usecases.user.token;

import com.mddcore.domain.models.RefreshToken;
import com.mddcore.domain.repository.IRefreshToken;
import com.mddcore.usecases.UseCase;
import com.mddcore.usecases.auth.IJwtExecFinal;
import com.mddcore.usecases.auth.TokenRequest;
import com.mddcore.usecases.user.DeleteUserUseCase;

import java.util.Map;

public class RefreshTokenUseCase extends UseCase<RefreshTokenUseCase.InputValues, RefreshTokenUseCase.OutputValues> {

    private final IRefreshToken refreshToken;
    private final IJwtExecFinal jwtExecFinal;

    public RefreshTokenUseCase(IRefreshToken refreshToken, IJwtExecFinal jwtExecFinal) {
        this.refreshToken = refreshToken;
        this.jwtExecFinal = jwtExecFinal;
    }

    @Override
    public OutputValues execute(InputValues input) {
        String requestToken = input.tokenRequest.getRefreshToken();

        refreshToken.findByToken(requestToken)
                .map(refreshTokenEntity -> {
                    refreshToken.delete(refreshTokenEntity);
                    Long userId = refreshTokenEntity.getUser().getId();
                    Map<String, Object> tokens = jwtExecFinal.refreshToken(userId);

                    RefreshToken.newInstance(
                            refreshTokenEntity.getUser(),
                            tokens.get("accessToken"),
                            tokens.get("refreshToken"),
                            in
                    )
                })
    }

    public record InputValues(TokenRequest tokenRequest) implements UseCase.InputValues {}

    public record OutputValues(Map<String, String> tokens) implements UseCase.OutputValues {}
}
