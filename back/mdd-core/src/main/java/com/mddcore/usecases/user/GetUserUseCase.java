package com.mddcore.usecases.user;

import com.mddcore.domain.models.User;
import com.mddcore.domain.repository.IUserRepository;
import com.mddcore.usecases.UseCase;

public class GetUserUseCase extends UseCase<GetUserUseCase.InputValues, GetUserUseCase.OutputValues> {
    private final IUserRepository userRepository;

    public GetUserUseCase(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public OutputValues execute(InputValues input) {
        User user = userRepository.findById(input.id()).orElse(null);

        if(user == null) {
            throw new IllegalArgumentException("User not found with userId : " + input.id());
        }

        return new OutputValues(user);
    }


    public record InputValues(Long id) implements UseCase.InputValues {}


    public record OutputValues(User user) implements UseCase.OutputValues {}
}
