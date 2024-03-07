package com.mddcore.usecases.user.token;

import com.mddcore.domain.repository.IRefreshTokenRepository;
import com.mddcore.usecases.UseCase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DeleteRefreshTokenUseCase extends UseCase<DeleteRefreshTokenUseCase.InputValues, DeleteRefreshTokenUseCase.OutputValues> {

    private final IRefreshTokenRepository repository;

    Logger logger = LoggerFactory.getLogger(DeleteRefreshTokenUseCase.class);

    public DeleteRefreshTokenUseCase(IRefreshTokenRepository repository) {
        this.repository = repository;
    }


    @Override
    public OutputValues execute(InputValues input) {
        try {
            logger.info("Delete refresh use case, before DeleteByUserId {}", input.userId());
            repository.deleteByUserId(input.userId());
            logger.info("Delete refresh use case, after DeleteByUserId");
            return new OutputValues();
        } catch (Exception e) {
            throw new IllegalArgumentException("Error while deleting token in db", e);
        }
    }

    public record InputValues(Long userId) implements UseCase.InputValues {}

    public record OutputValues() implements UseCase.OutputValues {}
}
