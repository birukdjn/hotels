package com.gobet.hotels.config;

import com.gobet.hotels.constants.RoleConstants;
import com.gobet.hotels.security.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session ->
                        session.sessionCreationPolicy(
                                SessionCreationPolicy.STATELESS
                        )
                )
                .authorizeHttpRequests(auth -> auth

                        // PUBLIC
                        .requestMatchers(
                                "/api/auth/**",
                                "/swagger-ui/**",
                                "/v3/api-docs/**",
                                "/api/bookings/public"
                        ).permitAll()

                        // HOTELS — PUBLIC READ
                        .requestMatchers(
                                HttpMethod.GET,
                                "/api/hotels",
                                "/api/hotels/*"
                        ).permitAll()

                        // ROOMS — PUBLIC READ
                        .requestMatchers(
                                HttpMethod.GET,
                                "/api/rooms/hotel/*",
                                "/api/rooms/*"
                        ).permitAll()

                        // HOTELS — SUPER ADMIN ONLY
                        .requestMatchers(
                                HttpMethod.POST,
                                "/api/hotels"
                        ).hasRole(RoleConstants.SUPER_ADMIN)

                        .requestMatchers(
                                HttpMethod.PUT,
                                "/api/hotels/*"
                        ).hasRole(RoleConstants.SUPER_ADMIN)

                        .requestMatchers(
                                HttpMethod.DELETE,
                                "/api/hotels/*"
                        ).hasRole(RoleConstants.SUPER_ADMIN)

                        // ADMIN APIs
                        .requestMatchers(
                                "/api/admin/**"
                        ).hasRole(RoleConstants.SUPER_ADMIN)

                        // METADATA (Regions, Zones, Cities) — PUBLIC READ
                        .requestMatchers(
                                HttpMethod.GET,
                                "/api/regions",
                                "/api/regions/*",
                                "/api/zones",
                                "/api/zones/*",
                                "/api/cities",
                                "/api/cities/*"
                        ).permitAll()

                        // METADATA — SUPER ADMIN WRITE
                        .requestMatchers(
                                HttpMethod.POST,
                                "/api/regions",
                                "/api/zones",
                                "/api/cities"
                        ).hasRole(RoleConstants.SUPER_ADMIN)

                        .requestMatchers(
                                HttpMethod.PUT,
                                "/api/regions/*",
                                "/api/zones/*",
                                "/api/cities/*"
                        ).hasRole(RoleConstants.SUPER_ADMIN)

                        .requestMatchers(
                                HttpMethod.PATCH,
                                "/api/regions/*",
                                "/api/zones/*",
                                "/api/cities/*"
                        ).hasRole(RoleConstants.SUPER_ADMIN)

                        .requestMatchers(
                                HttpMethod.DELETE,
                                "/api/regions/*",
                                "/api/zones/*",
                                "/api/cities/*"
                        ).hasRole(RoleConstants.SUPER_ADMIN)

                        // EVERYTHING ELSE
                        .anyRequest()
                        .authenticated()
                )
                .addFilterBefore(
                        jwtAuthenticationFilter,
                        UsernamePasswordAuthenticationFilter.class
                );

        return http.build();
    }
}