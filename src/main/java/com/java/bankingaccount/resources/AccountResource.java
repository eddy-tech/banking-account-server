package com.java.bankingaccount.resources;

import com.java.bankingaccount.dto.AccountDto;
import com.java.bankingaccount.services.AccountService;
import com.java.bankingaccount.utils.RootEntPoint;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(RootEntPoint.ROOT_ENDPOINT + "/accounts")
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
    @PostMapping("/")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<AccountDto> save(@RequestBody AccountDto dto) {
        return ResponseEntity.ok(accountService.save(dto));
    }

    @GetMapping("/")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<AccountDto>> getAll() {
        return ResponseEntity.ok(accountService.getAll());
    }

    @GetMapping("/{accountId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<AccountDto> getById(@PathVariable(name = "accountId") Integer id) {
        return ResponseEntity.ok(accountService.getById(id));
    }

    @DeleteMapping("/{accountId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable(name = "accountId")Integer id) {
        accountService.delete(id);
        return ResponseEntity.accepted().build();
    }
}
