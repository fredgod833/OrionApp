package com.mddpresenter.usecases.ut.security;

import com.mddcore.usecases.auth.SignInRequest;
import com.mddinfrastructure.security.jwt.CookieJwt;
import com.mddinfrastructure.security.usecases.AuthenticateUserUseCase;
import com.mddinfrastructure.security.userdetails.CustomUserDetails;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
public class AuthenticateUserUseCaseUnitTest {

    @InjectMocks
    private AuthenticateUserUseCase useCase;
    @Mock
    private AuthenticationManager authenticationManager;
    @Mock
    private CookieJwt jwtCookie;

    @Test
    public void AuthenticateUserUseCase_ShouldReturnCookieAndCustomUserDetails_WithValidRequest() {
        SignInRequest signInRequest = new SignInRequest("theo@test.com", "123456@testT");
        CustomUserDetails customUserDetails = mock(CustomUserDetails.class);
        Authentication authentication = mock(Authentication.class);
        ResponseCookie responseCookie = mock(ResponseCookie.class);

        AuthenticateUserUseCase.InputValues inputValues = new AuthenticateUserUseCase.InputValues(signInRequest);

        doReturn(authentication).when(authenticationManager).authenticate(new UsernamePasswordAuthenticationToken(
                signInRequest.email(), signInRequest.password()
        ));
        doReturn(customUserDetails).when(authentication).getPrincipal();
        doReturn(responseCookie).when(jwtCookie).generateJwtCookie(customUserDetails);

        AuthenticateUserUseCase.OutputValues outputValues = useCase.execute(inputValues);

        assertThat(outputValues.jwtCookie()).isEqualTo(responseCookie);
        assertThat(outputValues.userDetails()).isEqualTo(authentication.getPrincipal());
    }
}
