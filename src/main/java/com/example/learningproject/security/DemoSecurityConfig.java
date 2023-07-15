package com.example.learningproject.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class DemoSecurityConfig {

    @Bean
    public InMemoryUserDetailsManager userDetailsManager() {
        UserDetails marko = User.builder()
                .username("marko")
                .password("{noop}test123")
                .roles("EMPLOYEE")
                .build();

        UserDetails mary = User.builder()
                .username("mary")
                .password("{noop}test123")
                .roles("EMPLOYEE", "MANAGER")
                .build();

        UserDetails susan = User.builder()
                .username("susan")
                .password("{noop}test123")
                .roles("EMPLOYEE", "MANAGER", "ADMIN")
                .build();

        return new InMemoryUserDetailsManager(marko, mary, susan);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeHttpRequests(configurer ->
                configurer
                .requestMatchers(HttpMethod.GET, "/").hasRole("EMPLOYEE")
                .requestMatchers(HttpMethod.GET, "/api/students").hasRole("EMPLOYEE")
                .requestMatchers(HttpMethod.GET, "/api/students/**").hasRole("EMPLOYEE")
                .requestMatchers(HttpMethod.GET, "/api/students/name/**").hasRole("EMPLOYEE")
                .requestMatchers(HttpMethod.GET, "/api/students/email/**").hasRole("EMPLOYEE")
                .requestMatchers(HttpMethod.POST, "/api/students").hasRole("MANAGER"));

        httpSecurity.httpBasic(Customizer.withDefaults());

        httpSecurity.csrf(csrf -> csrf.disable());

        return httpSecurity.build();
    }
}
