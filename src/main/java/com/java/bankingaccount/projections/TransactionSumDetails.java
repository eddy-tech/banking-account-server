package com.java.bankingaccount.projections;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface TransactionSumDetails {
    LocalDate getTransactionDate();
    BigDecimal getAmount();
}
