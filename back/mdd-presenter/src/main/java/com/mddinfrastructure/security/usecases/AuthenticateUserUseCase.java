package com.mddinfrastructure.security.usecases;

import com.mddcore.usecases.UseCase;
import com.mddcore.usecases.auth.SignInRequest;
import com.mddinfrastructure.security.jwt.CookieJwt;
import com.mddinfrastructure.security.userdetails.CustomUserDetails;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticateUserUseCase  extends UseCase<AuthenticateUserUseCase.InputValues, AuthenticateUserUseCase.OutputValues> {
    private final AuthenticationManager authenticationManager;
    private final CookieJwt jwtCookie;

    public AuthenticateUserUseCase(AuthenticationManager authenticationManager, CookieJwt jwtCookie) {
        this.authenticationManager = authenticationManager;
        this.jwtCookie = jwtCookie;
    }

    @Override
    public  OutputValues execute(InputValues input) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                input.signInRequest.email(), input.signInRequest.password()
        ));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        ResponseCookie cookie = jwtCookie.generateJwtCookie((CustomUserDetails) authentication.getPrincipal());

        return new OutputValues(cookie, ((CustomUserDetails) authentication.getPrincipal()));
    }

    public record InputValues(SignInRequest signInRequest) implements UseCase.InputValues {}
    public record OutputValues(ResponseCookie jwtCookie, CustomUserDetails userDetails) implements UseCase.OutputValues {}

}
