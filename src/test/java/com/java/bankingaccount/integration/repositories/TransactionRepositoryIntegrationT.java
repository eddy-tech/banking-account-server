package com.java.bankingaccount.integration.repositories;

import com.java.bankingaccount.integration.AbstractionContainerBaseTest;
import com.java.bankingaccount.models.Transaction;
import com.java.bankingaccount.models.User;
import com.java.bankingaccount.projections.TransactionSumDetails;
import com.java.bankingaccount.repositories.TransactionRepository;
import com.java.bankingaccount.repositories.UserRepository;
import com.java.bankingaccount.utils.TransactionType;
import jakarta.validation.constraints.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class TransactionRepositoryIntegrationT extends AbstractionContainerBaseTest {
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private UserRepository userRepository;
    Transaction transaction;
    User user;

    @BeforeEach
    void setUp(){
        user = User.builder()
                .firstName("Toto")
                .lastName("hook")
                .email("hookto@gmail.com")
                .password("Azerty12358")
                .active(true)
                .createdDate(LocalDateTime.now())
                .build();

        transaction = Transaction.builder()
                .amount(BigDecimal.valueOf(5000))
                .transactionDate(LocalDate.now(ZoneId.of("Europe/Paris")))
                .type(TransactionType.TRANSFER)
                .destinationIban("FR7630004000031234567890143")
                .createdDate(LocalDateTime.now())
                .user(user)
                .build();
    }

    @Test
    void givenUserId_whenFindAccountBalance_thenReturnBigDecimalAmount(){
        transactionRepository.save(transaction);
        userRepository.save(user);

        var saveAmount = transactionRepository.findAccountBalance(user.getId());

        assertThat(saveAmount).isNotNull();
    }

    @Test
    void givenTransactionUserIdAndTypeTransaction_whenFindHighestAmountByTransactionType_thenReturnMaxAmount() {
        transactionRepository.save(transaction);
        userRepository.save(user);

        var highAmount = transactionRepository
                .findHighestAmountByTransactionType(user.getId(), TransactionType.TRANSFER);

        assertThat(highAmount).isNotNull();
    }

    @Test
    void givenLocalDateAndUserId_whenFindSumTransactionByDate_thenReturnListTransactionSumDetails(){
        transactionRepository.save(transaction);
        userRepository.save(user);

        var dataSum = transactionRepository
                .findSumTransactionByDate(LocalDateTime.now(), LocalDateTime.now(), user.getId());

        assertThat(dataSum).isNotNull();
    }
}
