package com.kimseungjin.cafe.config.docs;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@SecurityScheme(
        name = "Authorization",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        scheme = "bearer")
@Configuration
public class SwaggerConfig {

    private static final String API_DOCS_TITLE = "Cafe API 명세서";
    private static final String API_DOCS_DESCRIPTION = "Cafe 서비스 백엔드 서버 API 명세서";

    @Bean
    public OpenAPI openAPI(@Value("${springdoc.swagger-ui.version}") final String version) {
        final Info info =
                new Info().title(API_DOCS_TITLE).version(version).description(API_DOCS_DESCRIPTION);

        return new OpenAPI().components(new Components()).info(info);
    }
}
