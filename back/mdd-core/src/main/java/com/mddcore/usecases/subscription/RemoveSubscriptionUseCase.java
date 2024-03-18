package com.mddcore.usecases.subscription;

import com.mddcore.domain.repository.ISubscriptionRepository;
import com.mddcore.usecases.UseCase;

/**
 * Use case for removing a subscription by its ID.
 */
public class RemoveSubscriptionUseCase extends UseCase<RemoveSubscriptionUseCase.InputValues, RemoveSubscriptionUseCase.OutputValues> {
    private final ISubscriptionRepository repository;
    public RemoveSubscriptionUseCase(ISubscriptionRepository repository) {
        this.repository = repository;
    }

    /**
     * Removes a subscription given its ID. If the operation fails, it throws an exception.
     * @param input the input values containing the subscription ID to remove
     * @return the output values indicating the success of the removal
     * @throws IllegalArgumentException if there's an error during the deletion process
     */
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

