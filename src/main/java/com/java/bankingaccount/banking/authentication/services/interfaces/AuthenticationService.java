package com.java.bankingaccount.banking.authentication.services.interfaces;

import com.java.bankingaccount.banking.core.auth.AuthenticationResponse;
import com.java.bankingaccount.banking.core.auth.LoginRequest;
import com.java.bankingaccount.banking.user.dto.UserDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public interface AuthenticationService {
    UserDto register(UserDto userDto);
    Boolean verifyToken(String token);
    AuthenticationResponse authenticate(LoginRequest request);
    void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException;
}
