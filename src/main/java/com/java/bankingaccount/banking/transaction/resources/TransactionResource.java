package com.java.bankingaccount.banking.transaction.resources;

import com.java.bankingaccount.banking.transaction.dto.TransactionDto;
import com.java.bankingaccount.banking.core.models.HttpResponse;
import com.java.bankingaccount.banking.transaction.services.TransactionService;
import com.java.bankingaccount.core.utils.RootEntPoint;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static com.java.bankingaccount.banking.transaction.roots.TransactionEndpoint.*;
import static com.java.bankingaccount.core.utils.RootEntPoint.ROOT_ENDPOINT;
import static org.springframework.http.HttpStatus.ACCEPTED;

@RestController
@RequestMapping(ROOT_ENDPOINT + TRANSACTION_ENDPOINT)
@RequiredArgsConstructor
public class TransactionResource {
    private final TransactionService transactionService;

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    public ResponseEntity<HttpResponse> save(@RequestBody TransactionDto dto) {
        var newTransaction = transactionService.save(dto);
        return ResponseEntity.ok().body(
                HttpResponse.builder()
                        .timeStamp(LocalDateTime.now().toString())
                        .data(Map.of("new transaction created", newTransaction))
                        .message("Transaction is successfully created")
                        .status(HttpStatus.CREATED)
                        .statusCode(HttpStatus.CREATED.value())
                        .build()
        );
    }

    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    public ResponseEntity<List<TransactionDto>> getAll() {
        return ResponseEntity.ok(transactionService.getAll());
    }

    @GetMapping(TRANSACTION_ID_ENDPOINT)
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    public ResponseEntity<HttpResponse> getById(@PathVariable(name = "transactionId") Integer id) {
        var transaction = transactionService.getById(id);
        return ResponseEntity.ok().body(
                HttpResponse.builder()
                        .timeStamp(LocalDateTime.now().toString())
                        .data(Map.of("Transaction", transaction))
                        .message("Transaction information")
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .build()
        );
    }

    @GetMapping(TRANSACTION_USER_ENDPOINT)
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    public ResponseEntity<List<TransactionDto>> findAllByUser(@PathVariable(name = "userId") Integer userId) {
        return ResponseEntity.ok(transactionService.findAllByUser(userId));
    }

    @DeleteMapping(TRANSACTION_ID_ENDPOINT)
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    public ResponseEntity<HttpResponse> delete(@PathVariable(name = "transactionId")Integer id) {
        transactionService.delete(id);
        return ResponseEntity.accepted().body(
                HttpResponse.builder()
                        .timeStamp(LocalDateTime.now().toString())
                        .message("Transaction deleted")
                        .status(ACCEPTED)
                        .statusCode(ACCEPTED.value())
                        .build()
        );
    }

}
