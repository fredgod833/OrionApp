package com.mddcore.usecases.subscription;

import com.mddcore.domain.repository.ISubscriptionRepository;
import com.mddcore.usecases.UseCase;

public class RemoveSubscriptionUseCase extends UseCase<RemoveSubscriptionUseCase.InputValues, RemoveSubscriptionUseCase.OutputValues> {
    private final ISubscriptionRepository repository;
    public RemoveSubscriptionUseCase(ISubscriptionRepository repository) {
        this.repository = repository;
    }
    @Override
        public OutputValues execute(InputValues input) {
            try {
                repository.deleteById(input.subscriptionId());
                return new OutputValues(true);
            } catch (Exception e) {
                throw new IllegalArgumentException("Error while deleting subscription in db", e);
            }
        }

        public record InputValues(Long subscriptionId) implements UseCase.InputValues {}

        public record OutputValues(Boolean success) implements UseCase.OutputValues {}
}

