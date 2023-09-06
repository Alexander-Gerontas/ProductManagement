package com.alg.productmanager.config;

import com.alg.productmanager.authentication.UserDetailsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static com.alg.productmanager.objects.Enums.AccountType.ADMIN;
import static com.alg.productmanager.objects.Enums.AccountType.USER;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.csrf()
                .disable()
                .authorizeHttpRequests(request ->
                        request
                                .requestMatchers("/api/v1/account").permitAll()
                                .requestMatchers("/api/v1/account/registration").permitAll()
                                .requestMatchers("/api/v1/account/login").permitAll()
                                .requestMatchers("/api/v1/product/add").hasAuthority(USER.getLiteral())
                                .requestMatchers("/api/v1/product/get/{id}").hasAuthority(USER.getLiteral())
                                .requestMatchers("/api/v1/product/edit/{id}").hasAuthority(ADMIN.getLiteral())
                                .requestMatchers("/api/v1/product/delete/{id}").hasAuthority(ADMIN.getLiteral())
                                .anyRequest().authenticated()
                )
                .httpBasic();

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new UserDetailsServiceImpl();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }
}
