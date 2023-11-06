package com.java.bankingaccount.banking.transaction.projections;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface TransactionSumDetails {
    LocalDate getTransactionDate();
    BigDecimal getAmount();
}
