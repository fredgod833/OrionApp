    package com.mddcore.usecases;

    /**
     * Abstract class representing a use case.
     * @param <I> the type of input values for the use case
     * @param <O> the type of output values for the use case
     */
    public abstract class UseCase<I extends UseCase.InputValues, O extends UseCase.OutputValues> {
        public abstract O execute(I input);

        public interface InputValues {
        }

        public interface OutputValues {
        }

        public interface InputRequest {
        }
    }
