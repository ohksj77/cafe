package com.kimseungjin.cafe.config.security;

import com.kimseungjin.cafe.config.security.jwt.JwtFilter;
import com.kimseungjin.cafe.config.security.user.UserAuthDetailsService;
import com.kimseungjin.cafe.domain.member.entity.Role;

import jakarta.servlet.http.HttpServletResponse;

import lombok.RequiredArgsConstructor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.*;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private static final String[] AUTH_WHITELIST = {"swagger-ui/**", "v3/**", "members/signup"};
    private static final String ROLE_HIERARCHY = Role.ROLE_ADMIN + " > " + Role.ROLE_USER;
    private static final String ALLOW_ALL = "*";
    private static final String ALL_PATTERN = "/**";
    private final UserAuthDetailsService userAuthDetailsService;
    private final JwtFilter jwtFilter;

    @Bean
    public SecurityFilterChain configure(final HttpSecurity http) throws Exception {
        return http.cors(corsCustomizer())
                .csrf(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(
                        requests ->
                                requests.requestMatchers(AUTH_WHITELIST)
                                        .permitAll()
                                        .anyRequest()
                                        .authenticated())
                .sessionManagement(sessionManagementConfigurer())
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling(exceptionHandlingCustomizer())
                .userDetailsService(userAuthDetailsService)
                .headers(headersConfigurer())
                .build();
    }

    private Customizer<HeadersConfigurer<HttpSecurity>> headersConfigurer() {
        return headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable);
    }

    private Customizer<SessionManagementConfigurer<HttpSecurity>> sessionManagementConfigurer() {
        return session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    private Customizer<ExceptionHandlingConfigurer<HttpSecurity>> exceptionHandlingCustomizer() {
        return securityExceptionHandler -> {
            securityExceptionHandler.authenticationEntryPoint(
                    (request, response, authException) ->
                            response.sendError(HttpServletResponse.SC_UNAUTHORIZED));

            securityExceptionHandler.accessDeniedHandler(
                    (request, response, accessDeniedException) ->
                            response.sendError(HttpServletResponse.SC_FORBIDDEN));
        };
    }

    private Customizer<CorsConfigurer<HttpSecurity>> corsCustomizer() {
        return httpSecurityCorsConfigurer -> {
            final CorsConfiguration configuration = new CorsConfiguration();
            configuration.addAllowedOrigin(ALLOW_ALL);
            configuration.addAllowedHeader(ALLOW_ALL);
            configuration.addAllowedMethod(ALLOW_ALL);
            configuration.setAllowCredentials(true);

            final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
            source.registerCorsConfiguration(ALL_PATTERN, configuration);
        };
    }

    @Bean
    public RoleHierarchy roleHierarchy() {
        final RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();
        roleHierarchy.setHierarchy(ROLE_HIERARCHY);
        return roleHierarchy;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
