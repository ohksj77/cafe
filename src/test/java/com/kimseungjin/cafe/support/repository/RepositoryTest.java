package com.kimseungjin.cafe.support.repository;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.kimseungjin.cafe.support.database.ResetDatabase;
import com.kimseungjin.cafe.utils.EncryptUtils;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.mock.mockito.MockBean;

@EnableDataJpa
public abstract class RepositoryTest extends ResetDatabase {

    @MockBean private EncryptUtils encryptUtils;

    @BeforeEach
    void setup() {
        resetDatabase();
        when(encryptUtils.encrypt(any(String.class))).thenReturn("encrypt");
        when(encryptUtils.decrypt(any(String.class))).thenReturn("encrypt");
    }
}
