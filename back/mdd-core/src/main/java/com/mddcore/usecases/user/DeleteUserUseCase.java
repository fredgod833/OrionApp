package com.mddcore.usecases.user;

import com.mddcore.domain.models.Identity;
import com.mddcore.domain.models.User;
import com.mddcore.domain.repository.IUserRepository;
import com.mddcore.usecases.UseCase;
import com.mddcore.usecases.auth.securityAuth.IJwtExecFinal;

public class DeleteUserUseCase extends UseCase<DeleteUserUseCase.InputValues, DeleteUserUseCase.OutputValues> {
    private final IUserRepository userRepository;
    private final IJwtExecFinal jwtExecFinal;

    public DeleteUserUseCase(IUserRepository userRepository, IJwtExecFinal jwtExecFinal) {
        this.userRepository = userRepository;
        this.jwtExecFinal = jwtExecFinal;
    }

    @Override
    public OutputValues execute(InputValues input) {
        User user = userRepository.findById(input.id()).orElse(null);

        if(user == null) {
            throw new IllegalArgumentException("User not found, cant delete it");
        }


        if(!user.getId().equals(input.authId)) {
            throw new IllegalStateException("You cant delete other user except you");
        }

        userRepository.delete(user);

        return new OutputValues(true);
    }

    public record InputValues(Identity id, Identity authId) implements UseCase.InputValues {}


    public record OutputValues(boolean isDeleted) implements UseCase.OutputValues {}
}
