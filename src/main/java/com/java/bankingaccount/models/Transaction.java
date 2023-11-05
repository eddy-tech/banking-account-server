package com.java.bankingaccount.models;

import com.java.bankingaccount.utils.TransactionType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@SuperBuilder
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Transaction extends AbstractEntity{
    private BigDecimal amount;
    @Enumerated(EnumType.ORDINAL)
    private TransactionType type;
    private String destinationIban;
    @Column(updatable = false)
    private LocalDate transactionDate;

    @ManyToOne
    private User user;
}