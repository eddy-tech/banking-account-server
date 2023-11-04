package com.java.bankingaccount.banking.user.resource;

import com.java.bankingaccount.banking.core.auth.ChangePasswordRequest;
import com.java.bankingaccount.banking.user.dto.UserDto;
import com.java.bankingaccount.banking.core.models.HttpResponse;
import com.java.bankingaccount.banking.authentication.services.auth.LogoutService;
import com.java.bankingaccount.banking.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static com.java.bankingaccount.banking.user.roots.UserEndPoint.*;
import static com.java.bankingaccount.core.utils.RoleUtils.ADMIN;
import static com.java.bankingaccount.core.utils.RoleUtils.ADMIN_USER;
import static com.java.bankingaccount.core.utils.RootEntPoint.ROOT_ENDPOINT;
import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping(ROOT_ENDPOINT + USER_ENDPOINT)
@RequiredArgsConstructor
public class UserResource {
    private final UserService userService;
    private final LogoutService logoutService;

    @PostMapping
    @PreAuthorize(ADMIN)
    public ResponseEntity<HttpResponse> save(@RequestBody UserDto dto) {
        var newUser = userService.save(dto);
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
    @PreAuthorize(ADMIN)
    public ResponseEntity<List<UserDto>> getAll() {
        return ResponseEntity.ok(userService.getAll());
    }

    @GetMapping(USER_ID_ENDPOINT)
    @PreAuthorize(ADMIN)
    public ResponseEntity<HttpResponse> getById(@PathVariable("userId")Integer id) {
        var user = userService.getById(id);
        return ResponseEntity.ok().body(
                HttpResponse.builder()
                        .timeStamp(LocalDateTime.now().toString())
                        .data(Map.of("Success", user))
                        .message("User information")
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .build()
        );
    }

    @DeleteMapping(USER_ID_ENDPOINT)
    @PreAuthorize(ADMIN)
    public ResponseEntity<HttpResponse> delete(@PathVariable("userId") Integer id) {
        userService.delete(id);
        return ResponseEntity.accepted().body(
                HttpResponse.builder()
                        .timeStamp(LocalDateTime.now().toString())
                        .message("User deleted")
                        .status(ACCEPTED)
                        .statusCode(ACCEPTED.value())
                        .build()
        );
    }

    @PatchMapping(USER_VALIDATE_ENDPOINT)
    @PreAuthorize(ADMIN)
    public ResponseEntity<HttpResponse> validateAccount(@PathVariable("userId")Integer id) {
        var validate = userService.validateAccount(id);
        return ResponseEntity.ok().body(
                HttpResponse.builder()
                        .timeStamp(LocalDateTime.now().toString())
                        .data(Map.of("Validate user", validate))
                        .message("Account validate")
                        .status(OK)
                        .statusCode(OK.value())
                        .build()
        );
    }

    @PatchMapping(USER_INVALIDATE_ENDPOINT)
    @PreAuthorize(ADMIN)
    public ResponseEntity<HttpResponse> invalidateAccount(@PathVariable("userId")Integer id) {
        var invalidate = userService.invalidateAccount(id);
        return ResponseEntity.ok().body(
                HttpResponse.builder()
                        .timeStamp(LocalDateTime.now().toString())
                        .data(Map.of("Invalidate user", invalidate))
                        .message("Account deleted")
                        .status(OK)
                        .statusCode(OK.value())
                        .build()
        );
    }

    @PatchMapping(USER_CHANGE_PASSWORD_ENDPOINT)
    @PreAuthorize(ADMIN_USER)
    public ResponseEntity<?> changePassword(
            @RequestBody ChangePasswordRequest request,
            Principal connectedUser
    ){
        userService.changePassword(request, connectedUser);
        return ResponseEntity.accepted().build();
    }

    @PatchMapping(LOGOUT_ENDPOINT)
    @PreAuthorize(ADMIN_USER)
    public ResponseEntity<HttpResponse> logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        logoutService.logout(request, response, authentication);
        return ResponseEntity.ok().body(
                HttpResponse.builder()
                        .timeStamp(LocalDateTime.now().toString())
                        .message("User has been disconnected")
                        .status(OK)
                        .statusCode(OK.value())
                        .build()
        );
    }
}
