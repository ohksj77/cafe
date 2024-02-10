package com.kimseungjin.cafe.support.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kimseungjin.cafe.config.security.jwt.JwtFilter;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@WebMvcTest
@EnableWebMvc
@AutoConfigureMockMvc(addFilters = false)
public abstract class ControllerTest {

    @Autowired private ObjectMapper objectMapper;
    @MockBean private JpaMetamodelMappingContext jpaMetamodelMappingContext;
    @MockBean private JwtFilter jwtFilter;
    protected MockMvc mockMvc;

    protected String toRequestBody(final Object value) throws JsonProcessingException {
        return objectMapper.writeValueAsString(value);
    }

    @BeforeEach
    void setupMockMvc(final WebApplicationContext ctx) {
        mockMvc =
                MockMvcBuilders.webAppContextSetup(ctx)
                        .addFilter(new CharacterEncodingFilter("UTF-8", true))
                        .build();
    }
}
