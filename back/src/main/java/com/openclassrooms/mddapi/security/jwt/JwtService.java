package com.openclassrooms.mddapi.security.jwt;

import com.nimbusds.jose.jwk.source.ImmutableSecret;
import com.openclassrooms.mddapi.security.services.UserDetailsImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Service;

import javax.crypto.spec.SecretKeySpec;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

/**
 * Service de génération et de validation de token JWT
 */
@Slf4j
@Service
public class JwtService {

    private final String secret;

    private final JwtEncoder jwtEncoder;

    private final JwtDecoder jwtDecoder;

    /**
     * Constructeur
     * @param env : accès aux paramètress d'environnement
     */
    public JwtService(Environment env) {
        this.secret = env.getProperty("secret.key");
        SecretKeySpec secretKey = new SecretKeySpec(secret.getBytes(), "RSA");
        this.jwtEncoder = new NimbusJwtEncoder(new ImmutableSecret<>(secret.getBytes()));
        this.jwtDecoder = NimbusJwtDecoder.withSecretKey(secretKey).macAlgorithm(MacAlgorithm.HS256).build();
    }

    /**
     * Génère un Token JWT
     * @param user l'utilisateur à associer au token
     * @return le Token JWT encodé
     */
    public String generateAccessToken(UserDetailsImpl user) {
        Instant now = Instant.now();
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(now)
                .expiresAt(now.plus(1, ChronoUnit.DAYS))
                .subject(user.getUsername())
                .build();
        JwtEncoderParameters jwtEncoderParameters = JwtEncoderParameters.from(JwsHeader.with(MacAlgorithm.HS256).build(), claims);
        return jwtEncoder.encode(jwtEncoderParameters).getTokenValue();

    }

    /**
     * Controle un token JWT
     * @param token : le token Jwt encodé
     * @return true si le token est valide.
     */
    public boolean validateAccessToken(String token) {
        try {
            Jwt jwt = this.jwtDecoder.decode(token);
            //TODO : ajouter des controles (expiration, etc);
            return true;
        } catch (JwtException e) {
            log.error("Invalid Token !", e);
        }
        return false;
    }

    /**
     * Lit un token Jwt encodé
     * @param token : le token Jwt encodé
     * @return le token Jwt décodé
     */
    public Jwt readJwt(final String token) {
        return this.jwtDecoder.decode(token);
    }

}
