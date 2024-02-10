package com.kimseungjin.cafe.config.encrypt;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.encrypt.AesBytesEncryptor;

@Configuration
public class EncryptConfig {

    @Bean
    public AesBytesEncryptor aesBytesEncryptor(
            @Value("${encrypt.key}") final String key,
            @Value("${encrypt.salt}") final String salt) {
        return new AesBytesEncryptor(key, salt);
    }
}
