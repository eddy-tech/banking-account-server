package com.java.bankingaccount.banking.core.models;

import com.java.bankingaccount.core.utils.TransactionType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@SuperBuilder
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Transaction extends AbstractEntity implements Serializable {
    private BigDecimal amount;
    @Enumerated(EnumType.ORDINAL)
    private TransactionType type;
    private String destinationIban;
    @Column(updatable = false)
    private LocalDate transactionDate;

    @ManyToOne
    private User user;
}
