package com.java.bankingaccount.banking.authentication.services.auth;

import com.java.bankingaccount.banking.authentication.repository.AccessTokenRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

import static com.java.bankingaccount.core.utils.AuthenticationJWT.*;

@Service
@AllArgsConstructor
public class LogoutService implements LogoutHandler {
    private AccessTokenRepository accessTokenRepository;
    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        final String authHeader = request.getHeader(AUTHORIZATION);
        final String jwtToken;
        if(authHeader == null || !authHeader.startsWith(BEARER)) return;
        jwtToken = authHeader.substring(SUB_STRING);
        var storeToken = accessTokenRepository.findByAccessToken(jwtToken)
                .orElse(null);
        if(storeToken != null){
            storeToken.setExpired(true);
            storeToken.setRevoked(true);
            accessTokenRepository.save(storeToken);
        }
    }
}
