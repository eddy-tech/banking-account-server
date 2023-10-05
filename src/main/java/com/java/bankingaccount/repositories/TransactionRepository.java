package com.java.bankingaccount.repositories;

import com.java.bankingaccount.dto.TransactionDto;
import com.java.bankingaccount.models.Transaction;
import com.java.bankingaccount.projections.TransactionSumDetails;
import com.java.bankingaccount.utils.TransactionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface TransactionRepository extends JpaRepository<Transaction, Integer> {
    List<Transaction> findAllByUserId(Integer userId);
    @Query("select sum(t.amount) from Transaction t where t.user.id = :userId")
    BigDecimal findAccountBalance(@Param("userId") Integer userId);
    @Query("select max(abs(t.amount)) as maxAmount from Transaction t where t.user.id = :userId and t.type = :transactionType")
    BigDecimal findHighestAmountByTransactionType(@Param("userId") Integer userId, @Param("transactionType") TransactionType type);
    @Query("select min(abs(t.amount)) as minAmount from Transaction t where t.user.id = :userId and t.type = :transactionType")
    BigDecimal findLowestAmountByTransactionType(@Param("userId") Integer userId, @Param("transactionType") TransactionType type);
    @Query("select t.transactionDate as transactionDate, sum(t.amount) " +
            "from Transaction t " +
            "where t.user.id = :userId and t.createdDate between :start and :end " +
            "group by t.transactionDate")
    List<TransactionSumDetails> findSumTransactionByDate(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end, Integer userId);
}