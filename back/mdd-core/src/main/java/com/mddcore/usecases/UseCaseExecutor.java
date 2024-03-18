package com.mddcore.usecases;

import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

/**
 * Interface for executing use cases asynchronously.
 */
public interface UseCaseExecutor {

    /**
     * Executes the given use case asynchronously.
     * @param <RX> the type of result to be returned
     * @param <I> the type of input values for the use case
     * @param <O> the type of output values for the use case
     * @param useCase the use case to be executed
     * @param input the input values for the use case
     * @param outputMapper a function to map the output values to the desired result type
     * @return a CompletableFuture representing the result of the use case execution
     */
    <RX, I extends UseCase.InputValues, O extends UseCase.OutputValues> CompletableFuture<RX> execute(UseCase<I, O> useCase, I input, Function<O, RX> outputMapper);
}
