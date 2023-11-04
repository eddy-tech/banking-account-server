package com.java.bankingaccount.banking.authentication.resources;

import com.java.bankingaccount.banking.authentication.auth.LoginRequest;
import com.java.bankingaccount.banking.user.dto.UserDto;
import com.java.bankingaccount.banking.core.models.HttpResponse;
import com.java.bankingaccount.banking.authentication.services.auth.AuthenticationServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URI;
import java.time.LocalDateTime;
import java.util.Map;

import static com.java.bankingaccount.banking.authentication.roots.AuthenticationEndPoint.*;
import static com.java.bankingaccount.core.utils.RootEntPoint.ROOT_ENDPOINT;
import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping(ROOT_ENDPOINT + AUTH_ENDPOINT)
@RequiredArgsConstructor
public class AuthenticationResource {
    private final AuthenticationServiceImpl authenticationService;

    @PostMapping(REGISTER_ENDPOINT)
    public ResponseEntity<HttpResponse> register(@RequestBody UserDto user){
        var newUser = authenticationService.register(user);
        return ResponseEntity.created(URI.create("")).body(
                HttpResponse.builder()
                        .timeStamp(LocalDateTime.now().toString())
                        .data(Map.of("new user", newUser))
                        .message("User created")
                        .status(CREATED)
                        .statusCode(CREATED.value())
                        .build()
        );
    }

    @GetMapping
    public ResponseEntity<HttpResponse> confirmUserAccount(@RequestParam String token) {
        var isSuccess = authenticationService.verifyToken(token);
        return ResponseEntity.ok().body(
                HttpResponse.builder()
                        .timeStamp(LocalDateTime.now().toString())
                        .data(Map.of("Success", isSuccess))
                        .message("Account verified")
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .build()
        );
    }

    @PostMapping(AUTHENTICATION_ENDPOINT)
    public ResponseEntity<HttpResponse> authenticate (@RequestBody LoginRequest request){
        var login = authenticationService.authenticate(request);
        return ResponseEntity.ok().body(
                HttpResponse.builder()
                        .timeStamp(LocalDateTime.now().toString())
                        .data(Map.of("User is connected", login))
                        .message("User is successfully authenticated")
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .build()

        );
    }

    @PostMapping(REFRESH_ENDPOINT)
    public void refreshToken (HttpServletRequest request, HttpServletResponse response) throws IOException {
        authenticationService.refreshToken(request, response);
    }
}
