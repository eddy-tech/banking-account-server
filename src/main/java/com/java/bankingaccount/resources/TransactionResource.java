package com.java.bankingaccount.resources;

import com.java.bankingaccount.dto.TransactionDto;
import com.java.bankingaccount.services.TransactionService;
import com.java.bankingaccount.utils.RootEntPoint;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(RootEntPoint.ROOT_ENDPOINT + "/transactions")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class TransactionResource {
    private final TransactionService transactionService;

    @PostMapping("/")
    public ResponseEntity<TransactionDto> save(@RequestBody TransactionDto dto) {
        return ResponseEntity.ok(transactionService.save(dto));
    }

    @GetMapping("/")
    public ResponseEntity<List<TransactionDto>> getAll() {
        return ResponseEntity.ok(transactionService.getAll());
    }

    @GetMapping("/{transactionId}")
    public ResponseEntity<TransactionDto> getById(@PathVariable(name = "transactionId") Integer id) {
        return ResponseEntity.ok(transactionService.getById(id));
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<List<TransactionDto>> findAllByUser(@PathVariable(name = "userId") Integer userId) {
        return ResponseEntity.ok(transactionService.findAllByUser(userId));
    }

    @DeleteMapping("/{transactionId}")
    public ResponseEntity<Void> delete(@PathVariable(name = "transactionId")Integer id) {
        transactionService.delete(id);
        return ResponseEntity.accepted().build();
    }

}
