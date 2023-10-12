package com.java.bankingaccount.resources;

import com.java.bankingaccount.projections.TransactionSumDetails;
import com.java.bankingaccount.services.StatisticService;
import com.java.bankingaccount.utils.RootEntPoint;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(RootEntPoint.ROOT_ENDPOINT + "/statistics")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
public class StatisticResource {
    private final StatisticService statisticService;

    @GetMapping("/sum-by-date/{userId}")
    public ResponseEntity<List<TransactionSumDetails>> findSumTransactionByDate(
            @RequestParam(name = "start-date") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
            @RequestParam(name = "end-date") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate,
            @PathVariable(name = "userId") Integer userId) {
        return ResponseEntity.ok(statisticService.findSumTransactionByDate(startDate, endDate, userId));
    }

    @GetMapping("/account-balance/{userId}")
    public ResponseEntity<BigDecimal> getAccountBalance(@PathVariable(name = "userId") Integer userId) {
        return ResponseEntity.ok(statisticService.getAccountBalance(userId));
    }

    @GetMapping("/highest-transfer/{userId}")
    public ResponseEntity<BigDecimal> highestTransfer(@PathVariable(name = "userId") Integer userId) {
        return ResponseEntity.ok(statisticService.highestTransfer(userId));
    }

    @GetMapping("/highest-deposit/{userId}")
    public ResponseEntity<BigDecimal> highestDeposit(@PathVariable(name = "userId") Integer userId) {
        return ResponseEntity.ok(statisticService.highestDeposit(userId));
    }
}
