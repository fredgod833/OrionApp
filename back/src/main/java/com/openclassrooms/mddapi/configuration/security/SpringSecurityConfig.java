package com.openclassrooms.mddapi.configuration.security;

import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.security.oauth2.server.resource.web.BearerTokenAuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Security configuration
 */
@Configuration
@EnableWebSecurity
public class SpringSecurityConfig {

    /**
     * Layer service from spring
     */
    private final UserDetailsService userDetailsService;
    private final RsaKeyProperties rsaKeys;

    public SpringSecurityConfig(UserDetailsService userDetailsService, RsaKeyProperties rsaKeys ){
        this.userDetailsService = userDetailsService;
        this.rsaKeys = rsaKeys;
    }

    /**
     * Allow spring to know a channel of authenticated users
     * @return provider
     */
    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();

        /**
         * Set layer service
         */
        provider.setUserDetailsService(userDetailsService);
        /**
         * Password should be encoded
         */
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    /**
     * Set public and privates endpoints
     * @param http configuration
     * @return new configuration
     * @throws Exception to get errors
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.cors(cors -> cors.disable()).csrf(csrf -> csrf.disable())

                .authorizeRequests( auth -> auth
                        .antMatchers("/v2/api-docs", "/swagger-ui/**", "/swagger-resources/**", "/swagger-ui.html",
                                "/webjars/springfox-swagger-ui/**", "/api/auth/register", "/api/auth/login").permitAll()
                        .mvcMatchers("/api/auth/me","/api/subject/**", "/api/post/**",  "/api/user/**", "/api/subject/create_subject", "/api/user/comments","/api/user/comment", "/api/user/comment/{post_id}", "/api/auth/logout", "/api/user/delete/account/{id_user}").permitAll()
                        .anyRequest().authenticated())

                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                /**
                 * Ask for token authentication
                 */
                .oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt)
                .exceptionHandling( ex -> ex.authenticationEntryPoint(new BearerTokenAuthenticationEntryPoint()))
                .authenticationProvider(authenticationProvider());

        return http.build();
    }

    /**
     * Decode token with rsa key
     * @return decoder
     */
    @Bean
    public JwtDecoder jwtDecoder() {
        return NimbusJwtDecoder.withPublicKey(rsaKeys.getPublicKey()).build();
    }

    /**
     * Encode token with rsa key
     * @return encoder
     */
    @Bean
    JwtEncoder jwtEncoder() {
        JWK jwk = new RSAKey.Builder(rsaKeys.getPublicKey()).privateKey(rsaKeys.getPrivateKey()).build();
        JWKSource<SecurityContext> jwks = new ImmutableJWKSet<>(new JWKSet(jwk));
        return new NimbusJwtEncoder(jwks);
    }

    /**
     * Encode password
     * @return password encoded
     */
    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

}
