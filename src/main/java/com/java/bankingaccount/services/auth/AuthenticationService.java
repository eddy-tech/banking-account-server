package com.java.bankingaccount.services.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.java.bankingaccount.auth.LoginRequest;
import com.java.bankingaccount.auth.AuthenticationResponse;
import com.java.bankingaccount.dto.UserDto;
import com.java.bankingaccount.enums.TokenType;
import com.java.bankingaccount.models.User;
import com.java.bankingaccount.repositories.AccessTokenRepository;
import com.java.bankingaccount.repositories.UserRepository;
import com.java.bankingaccount.models.token.AccessToken;
import com.java.bankingaccount.validators.ObjectsValidator;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;

import static com.java.bankingaccount.utils.AuthenticationJWT.*;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final AccessTokenRepository tokenRepository;
    private final ObjectsValidator<UserDto> validator;

    public AuthenticationResponse register(UserDto userDto) {
        validator.validate(userDto);
        var user = UserDto.toUserDto(userDto);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        var saveUser = userRepository.save(user);
        var accessToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);

        revokeAllUserTokens(saveUser);
        saveUserToken(saveUser, accessToken);

        return buildToken(accessToken, refreshToken);
    }

    public AuthenticationResponse authenticate(LoginRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getEmail(),
                request.getPassword()
        ));

        var user = userRepository.findUserByEmail(request.getEmail())
                .orElseThrow(()-> new UsernameNotFoundException(String.format("User %s was not found", request.getEmail())));
        var accessToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);

        revokeAllUserTokens(user);
        saveUserToken(user, accessToken);

        return buildToken(accessToken, refreshToken);
    }

    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String userEmail;
        if(authHeader == null || !authHeader.startsWith(BEARER)) return;

        refreshToken = authHeader.substring(SUB_STRING);
        userEmail = jwtService.extractUsername(refreshToken);
        if(userEmail != null){
            var user = this.userRepository.findUserByEmail(userEmail)
                    .orElseThrow(()-> new UsernameNotFoundException(String.format("User %s was not found", userEmail)));

            if(jwtService.isTokenValid(refreshToken, user)){
                var accessToken = jwtService.generateToken(user);
                saveUserToken(user, accessToken);
                var authResponse = AuthenticationResponse.builder()
                        .accessToken(accessToken)
                        .refreshToken(refreshToken)
                        .build();

                new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
            }
        }
    }

    private void revokeAllUserTokens(User user){
        var validUserToken = tokenRepository.findAllValidAccessTokenByUser(user.getId());
        if(validUserToken.isEmpty())  return;
        validUserToken.forEach(token->{
            token.setExpired(true);
            token.setRevoked(true);
        });

        tokenRepository.saveAll(validUserToken);
    }

    private void saveUserToken(User user, String jwtToken){
        var accessToken = AccessToken.builder()
                .user(user)
                .accessToken(jwtToken)
                .tokenType(TokenType.BEARER)
                .revoked(false)
                .expired(false)
                .build();
        tokenRepository.save(accessToken);
    }

    private AuthenticationResponse buildToken(String jwtToken, String refreshToken){
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }
}
