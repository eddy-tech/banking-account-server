package com.java.bankingaccount.dto;

import com.java.bankingaccount.models.Transaction;
import com.java.bankingaccount.models.User;
import com.java.bankingaccount.utils.TransactionType;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Value;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * DTO for {@link com.java.bankingaccount.models.Transaction}
 */
@Data
@AllArgsConstructor
@Builder
public class TransactionDto implements Serializable {
    private Integer id;

    @Positive
    @Max(value = 100000000)
    @Min(value = 10)
    private BigDecimal amount;
    private TransactionType type;
    private String destinationIban;
    private Integer userId;
    private LocalDate transactionDate;

    public static TransactionDto fromTransaction(Transaction transaction) {
        return TransactionDto.builder()
                .id(transaction.getId())
                .amount(transaction.getAmount())
                .type(transaction.getType())
                .transactionDate(transaction.getTransactionDate())
                .destinationIban(transaction.getDestinationIban())
                .userId(transaction.getUser().getId())
                .build();
    }

    public static Transaction toTransactionDto(TransactionDto transaction) {
        return Transaction.builder()
                .id(transaction.getId())
                .amount(transaction.getAmount())
                .type(transaction.getType())
                .transactionDate(LocalDate.now())
                .destinationIban(transaction.getDestinationIban())
                .user(User.builder().id(transaction.getUserId()).build())
                .build();
    }
}