package com.java.bankingaccount.config;

import com.java.bankingaccount.services.auth.LogoutService;
import com.java.bankingaccount.utils.RootEntPoint;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static com.java.bankingaccount.roots.AuthenticationEndPoint.LOGOUT_ENDPOINT;
import static com.java.bankingaccount.utils.AuthenticationJWT.ROLE_ADMIN;
import static com.java.bankingaccount.utils.AuthenticationJWT.ROLE_USER;
import static com.java.bankingaccount.utils.RootEntPoint.ROOT_ENDPOINT;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {
    private static final String[] ENDPOINT_LIST = {
            "/**/authenticate",
            "/**/register",
            "/v2/api-docs",
            "/v2/api-docs/**",
            "/v3/api-docs",
            "/v3/api-docs/**",
            "/swagger-resources",
            "/swagger-resources/**",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-ui/**",
            "/webjars/**",
            "/swagger-ui.html"
    };

    private final JWTAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;
    private final LogoutService logoutService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(req ->
                        req.requestMatchers(
                                ENDPOINT_LIST
                        )
                                .permitAll()
                                .requestMatchers(ROOT_ENDPOINT + "/**").hasAnyRole(ROLE_ADMIN)
                                .requestMatchers(ROOT_ENDPOINT + "/users").hasAnyAuthority(ROLE_USER, ROLE_ADMIN)
                                .requestMatchers(ROOT_ENDPOINT + "/transactions").hasAnyAuthority(ROLE_USER, ROLE_ADMIN)
                                .requestMatchers(ROOT_ENDPOINT + "/contacts").hasAnyAuthority(ROLE_ADMIN)
                                .requestMatchers(ROOT_ENDPOINT + "/accounts").hasAnyAuthority(ROLE_ADMIN)
                                .anyRequest()
                                .authenticated()
                )
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .logout(logout ->
                        logout.logoutUrl(LOGOUT_ENDPOINT)
                                .addLogoutHandler(logoutService)
                                .logoutSuccessHandler(((request, response, authentication) ->
                                        SecurityContextHolder.clearContext()))
                        );

        return httpSecurity.build();
    }
}
