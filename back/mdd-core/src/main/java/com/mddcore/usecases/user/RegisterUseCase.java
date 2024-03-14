package com.mddcore.usecases.user;

import com.mddcore.domain.models.User;
import com.mddcore.domain.repository.IUserRepository;
import com.mddcore.usecases.UseCase;
import com.mddcore.usecases.auth.IPasswordEncodeFinal;
import com.mddcore.usecases.auth.SignInRequest;

public class RegisterUseCase extends UseCase<RegisterUseCase.InputValues, RegisterUseCase.OutputValues> {
    private final IUserRepository userRepository;
    private final IPasswordEncodeFinal passwordEncodeFinal;

    public RegisterUseCase(IUserRepository userRepository, IPasswordEncodeFinal passwordEncodeFinal) {
        this.userRepository = userRepository;
        this.passwordEncodeFinal = passwordEncodeFinal;
    }

    @Override
    public OutputValues execute(InputValues input) {
        String email = input.user().getEmail();
        String password = input.user().getPassword();
        String encodePass = passwordEncodeFinal.encodePass(password);

        if (userRepository.existByEmail(email)) {
            throw new IllegalArgumentException("User email already exist");
        }

        if (!validPassword(password)) {
            throw new IllegalArgumentException("Password security check failed");
        }

        User user = new User(null, email, input.user().getUsername(), encodePass, null, null);

        userRepository.save(user);

        return setSignInRequest(email, encodePass);
    }

    private Boolean validPassword(String password) {
        return password.length() >= 8 && password.matches("^(?=.*[A-Z])(?=.*[a-z])(?=.*[^a-zA-Z0-9]).*$");
    }

    private OutputValues setSignInRequest(String email, String password) {
        return new OutputValues(new SignInRequest(email, password));
    }

    public record InputValues(User user) implements UseCase.InputValues {
    }

    public record OutputValues(SignInRequest signInRequest) implements UseCase.OutputValues {
    }
}
