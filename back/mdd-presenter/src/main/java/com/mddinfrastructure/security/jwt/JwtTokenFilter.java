package com.mddinfrastructure.security.jwt;

import com.mddinfrastructure.security.userdetails.CustomUserDetails;
import com.mddinfrastructure.security.userdetails.CustomUserDetailsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtTokenFilter extends OncePerRequestFilter {
    private JwtTokenProvider jwtTokenProvider;
    private CustomUserDetailsService customUserDetailsService;
    private final Logger logger = LoggerFactory.getLogger(JwtTokenFilter.class);

    public JwtTokenFilter(JwtTokenProvider jwtTokenProvider, CustomUserDetailsService customUserDetailsService) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.customUserDetailsService = customUserDetailsService;
    }

    /**
     * Filtre les requêtes pour extraire et valider le jeton JWT.
     *
     * @param request La requête HTTP entrante.
     * @param response La réponse HTTP.
     * @param filterChain La chaîne de filtres.
     * @throws ServletException Si une erreur de servlet survient.
     * @throws IOException Si une erreur d'entrée/sortie survient.
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
//        try {
        String token = jwtTokenProvider.extractToken(request);
        if (token != null && jwtTokenProvider.validateToken(token)) {
            logger.info("Jwt getUsername = {}", jwtTokenProvider.extractUsername(token));
            UserDetails userDetails = customUserDetailsService.loadUserById(jwtTokenProvider.getUserId(token));
            logger.info("User details : {}", userDetails.toString());
            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            logger.info("auth principal : {}", authentication.getPrincipal());
        }
//        } catch (ExpiredJwtException ex) {
//            String isRefreshToken = request.getHeader("isRefreshToken");
//            String requestUrl = request.getRequestURL().toString();
//            if (isRefreshToken != null && isRefreshToken.equals("true") && requestUrl.contains("refreshtoken")) {
//                allowForRefreshToken(ex, request);
//            } else {
//                request.setAttribute("exception", ex);
//            }
//    }
        filterChain.doFilter(request, response);
    }

    public String refreshToken(String refreshToken) {
        Long user = jwtTokenProvider.getUserId(refreshToken);
        UserDetails userDetails = customUserDetailsService.loadUserById(user);
       return jwtTokenProvider.createRefreshToken((CustomUserDetails) userDetails);
    }

//    private void allowForRefreshToken(ExpiredJwtException ex, HttpServletRequest request) {
//        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
//                null, null, null
//        );
//        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
//        request.setAttribute("claims", ex.getClaims());
//    }
}
