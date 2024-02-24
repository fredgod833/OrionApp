package com.mddcore.usecases.user;

import com.mddcore.domain.models.Identity;
import com.mddcore.domain.models.User;
import com.mddcore.domain.repository.IUserRepository;
import com.mddcore.usecases.UseCase;
import com.mddcore.usecases.auth.securityAuth.IJwtExecFinal;
import com.mddcore.usecases.auth.securityAuth.IPasswordEncodeFinal;

public class UpdateUserUseCase extends UseCase<UpdateUserUseCase.InputValues, UpdateUserUseCase.OutputValues> {
    private final IUserRepository userRepository;
    private final IPasswordEncodeFinal passwordEncodeFinal;
    private final IJwtExecFinal jwtExecFinal;

    public UpdateUserUseCase(IUserRepository userRepository, IPasswordEncodeFinal passwordEncodeFinal, IJwtExecFinal jwtExecFinal) {
        this.userRepository = userRepository;
        this.passwordEncodeFinal = passwordEncodeFinal;
        this.jwtExecFinal = jwtExecFinal;
    }

    @Override
    public OutputValues execute(InputValues input) {
        return userRepository
                .findById(input.id())
                .map(user -> {
                    if (!user.getId().equals(input.authId)) {
                        throw new IllegalArgumentException("Authenticate user don't match user to update");
                    } else {
                            return persistAndReturn(user, input);
                    }
                })
                .orElseThrow(() -> new IllegalArgumentException("User to update not found"));
    }


    private OutputValues persistAndReturn(User user, InputValues input) {
        if (!input.user.getEmail().equals(user.getEmail())) {
            user.setEmail(input.user.getEmail());
        }

        if (!input.user.getUsername().equals(user.getUsername())) {
            user.setUsername(input.user.getUsername());
        }

        user.setPassword(passwordEncodeFinal.encodePass(input.user.getPassword()));

        return new OutputValues(userRepository.save(user));
    }



    public record InputValues(Identity id, User user, Identity authId) implements UseCase.InputValues { }


    public record OutputValues(User user) implements UseCase.OutputValues { }
}
