package com.java.bankingaccount.services;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;

public interface StatisticService {
    Map<LocalDate, BigDecimal> findSumTransactionByDate (LocalDate startDate, LocalDate endDate, Integer userId);
    //user account balance
    BigDecimal getAccountBalance(Integer userId);
    BigDecimal highestTransfer(Integer userId);
    BigDecimal highestDeposit(Integer userId);
}
