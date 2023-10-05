package com.openclassrooms.mddapi.configuration;

import com.openclassrooms.mddapi.configuration.model.Token;
import com.openclassrooms.mddapi.model.User;
import com.openclassrooms.mddapi.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtEncoder encoder;
    private final AuthenticationProvider authenticationProvider;

    public AuthService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder,
                       JwtEncoder encoder, AuthenticationProvider authenticationProvider){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.encoder = encoder;
        this.authenticationProvider = authenticationProvider;
    }

    // TODO: 22/09/2023 Login user
    public Token login(User user)  {

        try {
            if (user.getEmail().isEmpty()){
                throw new UserPrincipalNotFoundException("EMAIL NOT FOUND!!!");
            }

            User loguser = userRepository.getByEmail(user.getEmail()).orElse(null);

            if (loguser == null){
                return null;
            }

            if (!passwordEncoder.matches(user.getPassword(), loguser.getPassword())){
                throw new UserPrincipalNotFoundException("USER CREDENTIALS NOT MATCH");
            }

            User isAuthenticated = User.builder()
                    .email(loguser.getEmail())
                    .password(loguser.getPassword())
                    .build();

            authenticationProvider.authenticate(new UsernamePasswordAuthenticationToken(isAuthenticated.getEmail(), isAuthenticated.getPassword()));

            String token = generateToken(user);

            Token tokenObject = Token.builder()
                    .token(token)
                    .build();

            return tokenObject;

        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }

    public String generateToken(User user){
        Instant now = Instant.now();

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(now)
                .expiresAt(now.plus(1, ChronoUnit.HOURS))
                .subject(user.getEmail())
                .claim("scope", "")
                .build();

        //Signature
        return encoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }

    // TODO: 22/09/2023 Create user endPoint

    public User register(User user){

        if (user == null){
            return null;
        }

        User buildUser = User.builder()
                .username(user.getUsername())
                .lastname(user.getLastname())
                .email(user.getEmail())
                .password(passwordEncoder.encode(user.getPassword()))
                .build();
        try {
            if (buildUser.getEmail().isBlank() | buildUser.getUsername().isBlank() | buildUser.getPassword().isBlank()){
                throw new UserPrincipalNotFoundException("USER NOT VALID");
            }

            new UsernamePasswordAuthenticationToken(buildUser.getEmail(), buildUser.getPassword());

            userRepository.save(buildUser);

            return buildUser;
        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }
}
