package com.java.bankingaccount.services;

import com.java.bankingaccount.projections.TransactionSumDetails;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface StatisticService {
    List<TransactionSumDetails> findSumTransactionByDate (LocalDate startDate, LocalDate endDate, Integer userId);
    //user account balance
    BigDecimal getAccountBalance(Integer userId);
    BigDecimal highestTransfer(Integer userId);
    BigDecimal highestDeposit(Integer userId);
}
