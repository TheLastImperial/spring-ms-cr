package com.tli.oauth2.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityFiltersConfig {

    @Bean
    public SecurityFilterChain disableCsrf(HttpSecurity http) throws Exception {
        return http.csrf(AbstractHttpConfigurer::disable).build();
    }
}
