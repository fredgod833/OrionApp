package com.openclassrooms.mddapi.configuration.security;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

@ConfigurationProperties("rsa")
@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class RsaKeyProperties {
    RSAPrivateKey privateKey;
    RSAPublicKey publicKey;
}
