package com.optumcare.member_service.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {


    //Security config for mapping it with tokens and role based access control
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable());

        // Secure headers (good defaults + a few opinions)
        http.headers(h -> h
                .contentSecurityPolicy(csp -> csp.policyDirectives("default-src 'none'"))
                .frameOptions(f -> f.deny())
                .xssProtection(x -> {})
                .httpStrictTransportSecurity(hsts -> hsts.includeSubDomains(true)));

        http.authorizeHttpRequests(auth -> auth
                // actuator basics allowed
                .requestMatchers("/actuator/health", "/actuator/info").permitAll()

                // dev choice: keep swagger restricted or disable in prod via config
                .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").hasRole("ADMIN")

                // example: read endpoints vs write endpoints (tune per service)
                .requestMatchers(HttpMethod.GET, "/api/**").authenticated()
                .requestMatchers(HttpMethod.POST, "/api/**").authenticated()
                .requestMatchers(HttpMethod.PUT, "/api/**").authenticated()
                .requestMatchers(HttpMethod.DELETE, "/api/**").authenticated()

                // everything else
                .anyRequest().authenticated());

        http.oauth2ResourceServer(o -> o
                .jwt(j -> j.jwtAuthenticationConverter(this::convert)));

        return http.build();
    }

    private AbstractAuthenticationToken convert(Jwt jwt) {
        return new KeycloakJwtAuthConverter().convert(jwt);
    }
}
