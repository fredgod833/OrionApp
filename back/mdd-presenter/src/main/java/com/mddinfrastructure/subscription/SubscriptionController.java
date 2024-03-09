package com.mddinfrastructure.subscription;

import com.mddcore.usecases.UseCaseExecutor;
import com.mddcore.usecases.subscription.AddSubscriptionUseCase;
import com.mddcore.usecases.subscription.RemoveSubscriptionUseCase;
import com.mddinfrastructure.response.ApiResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.concurrent.CompletableFuture;

@Component
public class SubscriptionController implements SubscriptionResource {

    private final AddSubscriptionUseCase addSubscriptionUseCase;
    private final RemoveSubscriptionUseCase removeSubscriptionUseCase;
    private final UseCaseExecutor useCaseExecutor;

    public SubscriptionController(AddSubscriptionUseCase addSubscriptionUseCase,
                                  RemoveSubscriptionUseCase removeSubscriptionUseCase, UseCaseExecutor useCaseExecutor) {
        this.addSubscriptionUseCase = addSubscriptionUseCase;
        this.removeSubscriptionUseCase = removeSubscriptionUseCase;
        this.useCaseExecutor = useCaseExecutor;
    }

    @Override
    public CompletableFuture<ApiResponse> saveSubscription(@PathVariable Long userId,
                                                           @PathVariable Long subjectId) {
      return useCaseExecutor.execute(
                addSubscriptionUseCase,
                new AddSubscriptionUseCase.InputValues(userId, subjectId),
                outputValues -> {
                    if (outputValues.isAdded()) {
                        return new ApiResponse(true, "Add subscription successfully");
                    } else {
                        return new ApiResponse(false, "Error occurred while adding subscription");
                    }
                }
        );
    }
    @Override
    public CompletableFuture<ApiResponse> removeSubscription(@PathVariable Long subscriptionId) {
       return useCaseExecutor.execute(
                removeSubscriptionUseCase,
                new RemoveSubscriptionUseCase.InputValues(subscriptionId),
                outputValues -> {
                        if(outputValues.success()) {
                            return new ApiResponse(true, "Subscription removed with success");
                        } else {
                            return new ApiResponse(false, "Error occurred while removing subscription");
                        }
                }
        );
    }
}
