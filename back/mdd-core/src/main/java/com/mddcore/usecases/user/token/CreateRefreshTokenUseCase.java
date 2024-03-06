package com.mddcore.usecases.user.token;

import com.mddcore.domain.models.RefreshToken;
import com.mddcore.domain.models.User;
import com.mddcore.domain.repository.IRefreshTokenRepository;
import com.mddcore.domain.repository.IUserRepository;
import com.mddcore.usecases.UseCase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Instant;
import java.util.UUID;

public class CreateRefreshTokenUseCase extends UseCase<CreateRefreshTokenUseCase.InputValues, CreateRefreshTokenUseCase.OutputValues> {
    private final IRefreshTokenRepository repository;
    private final IUserRepository userRepository;

    public CreateRefreshTokenUseCase(IRefreshTokenRepository repository, IUserRepository userRepository) {
        this.repository = repository;
        this.userRepository = userRepository;
    }
    Logger logger = LoggerFactory.getLogger(CreateRefreshTokenUseCase.class);
    @Override
    public OutputValues execute(InputValues input) {
        User user = userRepository.findById(input.id).orElse(null);

        if (user == null) {
            throw new IllegalArgumentException("User do not exist");
        }

        logger.info("input expiry date = {}", input.expirationsMs());
        RefreshToken newRefreshToken = RefreshToken.newInstance(
                user,
                UUID.randomUUID().toString(),
                Instant.now().plusMillis(input.expirationsMs())
        );

        repository.save(newRefreshToken);
        return new OutputValues(newRefreshToken.getToken());
    }

    public record InputValues(Long id, Long expirationsMs) implements UseCase.InputValues {}

    public record OutputValues(String token) implements UseCase.OutputValues {}
}
