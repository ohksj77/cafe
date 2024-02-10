package com.kimseungjin.cafe.support.database;

import com.kimseungjin.cafe.config.database.FlywayConfig;

import org.springframework.boot.test.mock.mockito.MockBean;

public abstract class FlywayDisableTest {
    @MockBean private FlywayConfig flywayConfig;
}
