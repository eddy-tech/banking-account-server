package com.java.bankingaccount.resources;

import com.java.bankingaccount.services.StatisticService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;

@RestController
@RequestMapping("/statistics")
@RequiredArgsConstructor
public class StatisticResource {
    private final StatisticService statisticService;

    @GetMapping("/sum-by-date/{userId}")
    public ResponseEntity<Map<LocalDate, BigDecimal>> findSumTransactionByDate(
            @RequestParam(name = "startDate") LocalDate startDate,
            @RequestParam(name = "endDate") LocalDate endDate,
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
