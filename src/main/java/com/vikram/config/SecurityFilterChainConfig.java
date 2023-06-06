package com.vikram.config;

import com.vikram.security.exception.JWTExceptionHandler;
import com.vikram.security.filter.JWTTokenFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
public class SecurityFilterChainConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(JWTTokenFilter jwtTokenFilter,
                                                   HttpSecurity httpSecurity,
                                                   AuthenticationProvider authenticationProvider,
                                                   JWTExceptionHandler jwtExceptionHandler) throws Exception {

        return httpSecurity
                .csrf(csrf -> {
                    csrf
                            .ignoringRequestMatchers(AntPathRequestMatcher.antMatcher("/h2-console/**"))
                            .disable();
                })
                .formLogin(AbstractHttpConfigurer::disable)
                .logout(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth ->
                        auth
                                //paths without security
                                .requestMatchers(AntPathRequestMatcher.antMatcher("/h2-console/**")).permitAll()
                                .requestMatchers(AntPathRequestMatcher.antMatcher("/account/v1/**")).permitAll()
                                //other parts require security
                                .anyRequest().authenticated()
                )
                // fix H2 database console: Refused to display ' in a frame because it set 'X-Frame-Options' to 'deny'
                .headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable))
                .sessionManagement(sessionManagement ->
                        sessionManagement
                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .exceptionHandling(httpSecurityExceptionHandlingConfigurer ->
                        httpSecurityExceptionHandlingConfigurer.authenticationEntryPoint(jwtExceptionHandler))
                .addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
}
