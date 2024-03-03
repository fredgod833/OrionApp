package com.mddinfrastructure.security.jwt;

import com.mddinfrastructure.security.userdetails.CustomUserDetails;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.security.Key;
import java.util.Date;
import java.util.function.Function;

@Component
@Slf4j
public class JwtTokenProvider {

    Key key = Keys.secretKeyFor(SignatureAlgorithm.HS512);

    @Value("${mdd.app.jwtExpirationsMs}")
    private long jwtExpiration;

    @Value("${mdd.app.jwtRefreshExpirationsMs}")
    private long refreshExpiration;

    /**
     * Crée un jeton JWT pour un utilisateur authentifié.
     *
     * @return Un jeton JWT signé.
     */
    public String createToken(CustomUserDetails userDetails) {
        Date now = new Date();
        Date expireDate = new Date(now.getTime() + jwtExpiration);
        return Jwts.builder()
                .setSubject(String.valueOf(userDetails.getUser().getId()))
                .setIssuedAt(new Date())
                .setExpiration(expireDate)
                .signWith(SignatureAlgorithm.HS512, key)
                .compact();
    }

    public String createRefreshToken(CustomUserDetails userDetails) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + refreshExpiration); // refreshExpiration est plus long que jwtExpiration

        return Jwts.builder()
                .setSubject(String.valueOf(userDetails.getUser().getId()))
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(key)
                .compact();
    }


    /**
     * Extrait le jeton JWT de la requête HTTP.
     *
     * @param request La requête HTTP.
     * @return Le jeton JWT ou null si aucun jeton n'est trouvé.
     */
    public String extractToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if(StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    /**
     * Valide le jeton JWT.
     *
     * @param token Le jeton JWT à valider.
     * @return True si le jeton est valide, False sinon.
     */
    public Boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(key).parseClaimsJws(token);
            return true;
        } catch (MalformedJwtException e) {
            log.error("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            log.error("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            log.error("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            log.error("JWT claims string is empty");
        } catch (SignatureException e) {
            log.error("there is an error with the signature of you token");
        }
        return false;
    }

    /**
     * Récupère le nom d'utilisateur à partir du jeton JWT.
     *
     * @param token Le jeton JWT.
     * @return Le nom d'utilisateur.
     */
    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public Date getExpirationDate(String token) {
        return extractAllClaims(token).getExpiration();
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public Long getUserId(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(key)
                .parseClaimsJws(token)
                .getBody();
        return Long.parseLong(claims.getSubject());
    }
}
