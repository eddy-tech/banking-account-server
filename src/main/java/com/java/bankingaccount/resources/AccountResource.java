package com.java.bankingaccount.resources;

import com.java.bankingaccount.dto.AccountDto;
import com.java.bankingaccount.services.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/accounts")
@RequiredArgsConstructor
public class AccountResource {
    private final AccountService accountService;

    @PostMapping("/")
    public ResponseEntity<Integer> save(@RequestBody AccountDto dto) {
        return ResponseEntity.ok(accountService.save(dto));
    }

    @GetMapping("/")
    public ResponseEntity<List<AccountDto>> getAll() {
        return ResponseEntity.ok(accountService.getAll());
    }

    @GetMapping("/{accountId}")
    public ResponseEntity<AccountDto> getById(@PathVariable(name = "accountId") Integer id) {
        return ResponseEntity.ok(accountService.getById(id));
    }

    @DeleteMapping("/{accountId}")
    public ResponseEntity<Void> delete(@PathVariable(name = "accountId")Integer id) {
        accountService.delete(id);
        return ResponseEntity.accepted().build();
    }
}
