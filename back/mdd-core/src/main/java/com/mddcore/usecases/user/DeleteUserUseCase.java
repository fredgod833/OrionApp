package com.mddcore.usecases.user;

import com.mddcore.domain.models.User;
import com.mddcore.domain.repository.IUserRepository;
import com.mddcore.usecases.UseCase;

public class DeleteUserUseCase extends UseCase<DeleteUserUseCase.InputValues, DeleteUserUseCase.OutputValues> {
    private final IUserRepository userRepository;

    public DeleteUserUseCase(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public OutputValues execute(InputValues input) {
        User user = userRepository.findById(input.id()).orElseThrow(() ->
                new IllegalArgumentException("User not found, cant delete it"));


        if (!user.getId().equals(input.authId)) {
            throw new IllegalStateException("You cant delete other user except you");
        }

        userRepository.delete(user);

        return new OutputValues(true);
    }

    public record InputValues(Long id, Long authId) implements UseCase.InputValues {
    }


    public record OutputValues(boolean isDeleted) implements UseCase.OutputValues {
    }
}
