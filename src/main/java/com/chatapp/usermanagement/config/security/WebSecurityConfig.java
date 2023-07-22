package com.chatapp.usermanagement.config.security;


import com.chatapp.usermanagement.config.rest.RestProperties;
import com.chatapp.usermanagement.config.security.filter.SecurityFilter;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@EnableWebSecurity
@EnableGlobalAuthentication
@RequiredArgsConstructor
@EnableMethodSecurity(securedEnabled = true)
@Configuration
public class WebSecurityConfig {

    private final String SPRING_ACTUATOR_PATH = "/actuator";
    private final String ALLOW_ALL_ENDPOINTS = "/**";


    @Lazy
    private final SecurityFilter securityFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(final HttpSecurity http) throws Exception {
        http
                .securityMatcher(AntPathRequestMatcher
                        .antMatcher(RestProperties.ROOT + "/v1" + ALLOW_ALL_ENDPOINTS));
        http.csrf(AbstractHttpConfigurer::disable);

        // to handle login
        http
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable);
        // setting up stateless authentication
        http.sessionManagement(
                        sessionManagement -> sessionManagement
                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                                .sessionAuthenticationFailureHandler((req, rsp, e) ->
                                        rsp.sendError(HttpServletResponse.SC_UNAUTHORIZED)
                                )
                ).authorizeHttpRequests(
                        arm ->
                                arm.requestMatchers(HttpMethod.OPTIONS).permitAll()
                                        .requestMatchers(allowedGetEndpoints()).permitAll()
                                        .anyRequest().authenticated()

                )
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    @Order(2)
    public SecurityFilterChain h2ConsoleSecurityFilterChain(final HttpSecurity http) throws Exception {
        http.securityMatcher(AntPathRequestMatcher.antMatcher("/h2-console/**"))
                .authorizeHttpRequests(auth ->
                        auth
                                .requestMatchers(AntPathRequestMatcher.antMatcher("/h2-console/**")).permitAll()
                )
                .csrf(AbstractHttpConfigurer::disable)
                .headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin));
        return http.build();
    }

    private String[] allowedGetEndpoints() {
        return new String[]{
                SPRING_ACTUATOR_PATH + ALLOW_ALL_ENDPOINTS,
                "/swagger-ui.html" + ALLOW_ALL_ENDPOINTS,
                "/swagger-ui" + ALLOW_ALL_ENDPOINTS,
                "/api-docs" + ALLOW_ALL_ENDPOINTS,
                "/error" + ALLOW_ALL_ENDPOINTS,
                "/error",
                "/h2-console",
                "/h2-console" + ALLOW_ALL_ENDPOINTS
        };
    }


}
