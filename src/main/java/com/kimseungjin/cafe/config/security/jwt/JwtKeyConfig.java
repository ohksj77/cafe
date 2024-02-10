package com.kimseungjin.cafe.config.security.jwt;

import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.security.Key;

@Configuration
public class JwtKeyConfig {

    private final String secretKey;

    public JwtKeyConfig(@Value("${token.secret-key}") String secretKey) {
        this.secretKey = secretKey;
    }

    @Bean
    public Key key() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));
    }
}
