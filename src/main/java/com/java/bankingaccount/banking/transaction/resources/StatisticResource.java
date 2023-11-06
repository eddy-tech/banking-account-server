package com.java.bankingaccount.banking.transaction.resources;

import com.java.bankingaccount.banking.core.models.HttpResponse;
import com.java.bankingaccount.banking.transaction.projections.TransactionSumDetails;
import com.java.bankingaccount.banking.transaction.services.StatisticService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static com.java.bankingaccount.banking.transaction.roots.StatisticEndpoint.*;
import static com.java.bankingaccount.core.utils.RoleUtils.ADMIN_USER;
import static com.java.bankingaccount.core.utils.RootEntPoint.ROOT_ENDPOINT;

@RestController
@RequestMapping(ROOT_ENDPOINT + STATISTIC_ENDPOINT)
@RequiredArgsConstructor
public class StatisticResource {
    private final StatisticService statisticService;

    @GetMapping(SUM_BY_DATE_ENDPOINT)
    @PreAuthorize(ADMIN_USER)
    public ResponseEntity<List<TransactionSumDetails>> findSumTransactionByDate(
            @RequestParam(name = "start-date") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
            @RequestParam(name = "end-date") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate,
            @PathVariable(name = "userId") Integer userId) {
        return ResponseEntity.ok(statisticService.findSumTransactionByDate(startDate, endDate, userId));
    }

    @GetMapping(ACCOUNT_BALANCE_ENDPOINT)
    @PreAuthorize(ADMIN_USER)
    public ResponseEntity<HttpResponse> getAccountBalance(@PathVariable(name = "userId") Integer userId) {
        var balance = statisticService.getAccountBalance(userId);
        return ResponseEntity.ok().body(
                HttpResponse.builder()
                        .timeStamp(LocalDateTime.now().toString())
                        .data(Map.of("Balance", balance))
                        .message("New Balance of your account")
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .build()
        );
    }

    @GetMapping(HIGHEST_TRANSFER_ENDPOINT)
    @PreAuthorize(ADMIN_USER)
    public ResponseEntity<HttpResponse> highestTransfer(@PathVariable(name = "userId") Integer userId) {
        var highestTransfer = statisticService.highestTransfer(userId);
        return ResponseEntity.ok().body(
                HttpResponse.builder()
                        .timeStamp(LocalDateTime.now().toString())
                        .data(Map.of("Highest Transfer", highestTransfer))
                        .message("New highest transfer of your account")
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .build()
        );
    }

    @GetMapping(HIGHEST_DEPOSIT_ENDPOINT)
    @PreAuthorize(ADMIN_USER)
    public ResponseEntity<HttpResponse> highestDeposit(@PathVariable(name = "userId") Integer userId) {
        var highestDeposit = statisticService.highestDeposit(userId);
        return ResponseEntity.ok().body(
                HttpResponse.builder()
                        .timeStamp(LocalDateTime.now().toString())
                        .data(Map.of("Highest Deposit", highestDeposit))
                        .message("New highest deposit of your account")
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .build()
        );
    }
}
