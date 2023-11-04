package com.java.bankingaccount.banking.transaction.repository;

import com.java.bankingaccount.banking.core.models.Transaction;
import com.java.bankingaccount.banking.transaction.projections.TransactionSumDetails;
import com.java.bankingaccount.core.utils.TransactionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Integer> {
    List<Transaction> findAllByUserId(Integer userId);
    @Query("select sum(t.amount) from Transaction t where t.user.id = :userId")
    BigDecimal findAccountBalance(@Param("userId") Integer userId);
    @Query("select max(abs(t.amount)) as maxAmount from Transaction t where t.user.id = :userId and t.type = :transactionType")
    BigDecimal findHighestAmountByTransactionType(@Param("userId") Integer userId, @Param("transactionType") TransactionType type);
    @Query("select min(abs(t.amount)) as minAmount from Transaction t where t.user.id = :userId and t.type = :transactionType")
    BigDecimal findLowestAmountByTransactionType(@Param("userId") Integer userId, @Param("transactionType") TransactionType type);
    @Query("select t.transactionDate as transactionDate, sum(t.amount) as amount " +
            "from Transaction t where t.user.id = :userId and t.createdDate between :start and :end " +
            "group by t.transactionDate")
    List<TransactionSumDetails> findSumTransactionByDate(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end, @Param("userId") Integer userId);
}