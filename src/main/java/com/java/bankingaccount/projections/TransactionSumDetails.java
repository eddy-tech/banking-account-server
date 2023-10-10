package com.java.bankingaccount.projections;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public interface TransactionSumDetails {
    LocalDate getTransactionDate();
    BigDecimal getAmount();
}
