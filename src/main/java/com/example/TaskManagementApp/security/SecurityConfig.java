package com.example.TaskManagementApp.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/register", "/login").permitAll() // Разрешаем доступ к этим путям без аутентификации
                        .anyRequest().authenticated() // Все остальные запросы требуют аутентификации
                )
                .formLogin(form -> form
                        .loginPage("/login") // Указываем кастомную страницу логина
                        .permitAll() // Разрешаем всем доступ к странице логина
                )
                .logout(logout -> logout
                        .permitAll() // Разрешаем всем доступ к странице логаута
                );

        return http.build();
    }
}

