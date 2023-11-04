package com.java.bankingaccount.banking.account.resource;

import com.java.bankingaccount.banking.account.dto.AccountDto;
import com.java.bankingaccount.banking.core.models.HttpResponse;
import com.java.bankingaccount.banking.account.service.AccountService;
import com.java.bankingaccount.core.utils.RootEntPoint;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static com.java.bankingaccount.banking.account.roots.AccountEndPoint.ACCOUNT_ENDPOINT;
import static com.java.bankingaccount.banking.account.roots.AccountEndPoint.ACCOUNT_ID_ENDPOINT;
import static com.java.bankingaccount.core.utils.RootEntPoint.ROOT_ENDPOINT;
import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping(ROOT_ENDPOINT + ACCOUNT_ENDPOINT)
@RequiredArgsConstructor
public class AccountResource {
    private final AccountService accountService;

    @Operation(
            description = "Get endpoint for Admin",
            summary = "This is a summary for account endpoint",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Unauthorized/ Invalid token",
                            responseCode = "403"
                    )
            }
    )
    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<HttpResponse> save(@RequestBody AccountDto dto) {
        var newAccount = accountService.save(dto);
        return ResponseEntity.created(URI.create("")).body(
                HttpResponse.builder()
                        .timeStamp(LocalDateTime.now().toString())
                        .data(Map.of("new account", newAccount))
                        .message("Account created")
                        .status(CREATED)
                        .statusCode(CREATED.value())
                        .build()
        );
    }

    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<AccountDto>> getAll() {
        return ResponseEntity.ok(accountService.getAll());
    }

    @GetMapping(ACCOUNT_ID_ENDPOINT)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<HttpResponse> getById(@PathVariable(name = "accountId") Integer id) {
        var account = accountService.getById(id);
        return ResponseEntity.ok().body(
                HttpResponse.builder()
                        .timeStamp(LocalDateTime.now().toString())
                        .data(Map.of("user List", account))
                        .message("All users lists")
                        .status(OK)
                        .statusCode(OK.value())
                        .build()
        );
    }

    @DeleteMapping(ACCOUNT_ID_ENDPOINT)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<HttpResponse> delete(@PathVariable(name = "accountId")Integer id) {
        accountService.delete(id);
        return ResponseEntity.accepted().body(
                HttpResponse.builder()
                        .timeStamp(LocalDateTime.now().toString())
                        .message("User deleted")
                        .status(ACCEPTED)
                        .statusCode(ACCEPTED.value())
                        .build()
        );
    }
}
