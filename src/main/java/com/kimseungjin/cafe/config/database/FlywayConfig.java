package com.kimseungjin.cafe.config.database;

import lombok.RequiredArgsConstructor;

import org.flywaydb.core.Flyway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
@RequiredArgsConstructor
public class FlywayConfig {

    private static final String LOCATION = "classpath:db/migration";
    private final DataSource dataSource;

    @Bean(initMethod = "migrate")
    public Flyway flyway() {
        return Flyway.configure().dataSource(dataSource).locations(LOCATION).load();
    }
}
