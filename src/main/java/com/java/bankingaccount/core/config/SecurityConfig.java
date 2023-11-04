package com.java.bankingaccount.core.config;

import com.java.bankingaccount.banking.authentication.services.auth.LogoutService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.CorsConfigurer;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static com.java.bankingaccount.banking.user.roots.UserEndPoint.LOGOUT_ENDPOINT;
import static com.java.bankingaccount.banking.user.roots.UserEndPoint.USER_ENDPOINT;
import static com.java.bankingaccount.core.enums.Roles.*;
import static com.java.bankingaccount.core.utils.RootEntPoint.ROOT_ENDPOINT;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {
    private static final String[] ENDPOINT_LIST = {
            "/api/v1/root/auth/**",
            ROOT_ENDPOINT + "/**",
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
                .cors(CorsConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(req ->
                        req.requestMatchers(
                                ENDPOINT_LIST
                        )
                                .permitAll()
                                .requestMatchers(ROOT_ENDPOINT + "/users/**").hasRole(ADMIN.name())
                                .requestMatchers(ROOT_ENDPOINT + "/transactions/**").hasAnyAuthority(USER.name(), ADMIN.name())
                                .requestMatchers(ROOT_ENDPOINT + "/contacts/**").hasRole(ADMIN.name())
                                .requestMatchers(ROOT_ENDPOINT + "/accounts/**").hasRole(ADMIN.name())
                                .requestMatchers(ROOT_ENDPOINT + "/address/**").hasAnyRole(ADMIN.name(), USER.name())
                                .anyRequest()
                                .authenticated()
                )
                .sessionManagement(session ->
                        session.sessionCreationPolicy(STATELESS)
                )
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .logout(logout ->
                        logout.logoutUrl(USER_ENDPOINT + LOGOUT_ENDPOINT)
                                .addLogoutHandler(logoutService)
                                .logoutSuccessHandler(((request, response, authentication) ->
                                        SecurityContextHolder.clearContext()))
                        );

        return httpSecurity.build();
    }
}
