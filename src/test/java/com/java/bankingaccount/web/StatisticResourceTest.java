package com.java.bankingaccount.web;

import com.java.bankingaccount.banking.transaction.projections.TransactionSumDetails;
import com.java.bankingaccount.banking.transaction.repository.TransactionRepository;
import com.java.bankingaccount.banking.transaction.resources.StatisticResource;
import com.java.bankingaccount.banking.transaction.services.StatisticServiceImpl;
import jakarta.validation.constraints.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(StatisticResource.class)
class StatisticResourceTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private StatisticServiceImpl statisticService;
    @Mock
    private TransactionRepository transactionRepository;
    String requestMapping = "/api/v1/statistics";
    String requestId = "{userId}";
    LocalDate startDate = LocalDate.of(2023, Month.JUNE, 7);
    LocalDate endDate = LocalDate.of(2023, Month.DECEMBER, 7);
    Integer userId = 1;

    @BeforeEach
    void setUp(){
    }

    @NotNull
    private static List<TransactionSumDetails> getTransactionSumDetails() {
        List<TransactionSumDetails> transactionSumDetailsList = new ArrayList<>();
        transactionSumDetailsList.add(
                new TransactionSumDetails() {
                    @Override
                    public LocalDate getTransactionDate() {
                        return LocalDate.of(2023, Month.JUNE, 7) ;
                    }

                    @Override
                    public BigDecimal getAmount() {
                        return BigDecimal.valueOf(100.5);
                    }
                }
        );
        transactionSumDetailsList.add(
                new TransactionSumDetails() {
                    @Override
                    public LocalDate getTransactionDate() {
                        return LocalDate.of(2023, Month.DECEMBER, 7);
                    }

                    @Override
                    public BigDecimal getAmount() {
                        return BigDecimal.valueOf(200.5);
                    }
                }
        );
        return transactionSumDetailsList;
    }

    @Test
    void givenLocalDateAndUserId_whenFindSumTransactionByDate_thenReturnListTransactionSumDetails_200() throws Exception {
        List<TransactionSumDetails> transactionSumDetailsList = getTransactionSumDetails();
        given(statisticService.findSumTransactionByDate(startDate, endDate, userId))
                .willReturn(transactionSumDetailsList);

        var resultActions = mockMvc
                .perform(get(requestMapping + "/sum-by-date/" + requestId, userId)
                        .param("startDate", "2023-06-07")
                        .param("endDate", "2023-12-07")
                        .contentType(MediaType.APPLICATION_JSON));

        resultActions.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(transactionSumDetailsList.size())))
                .andExpect(jsonPath("$[0].transactionDate", is("2023-06-07")))
                .andExpect(jsonPath("$[0].amount", is(100.5)));
    }

    @Test
    void givenUserId_whenGetAccountBalance_thenReturnAmount200() throws Exception {
        given(statisticService.getAccountBalance(userId))
                .willReturn(BigDecimal.valueOf(3000.5));

        var resultActions = mockMvc
                .perform(get(requestMapping + "/account-balance/" + requestId, userId));

        resultActions.andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void givenUserId_whenHighestTransfer_thenReturnHighestAmountByTransfer200() throws Exception {
        given(statisticService.highestTransfer(userId))
                .willReturn(BigDecimal.valueOf(5000.5));

        var resultActions = mockMvc
                .perform(get(requestMapping + "/highest-transfer/" + requestId, userId));

        resultActions.andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void givenUserId_whenHighestDeposit_thenReturnHighestAmountByDeposit200() throws Exception {
        given(statisticService.highestDeposit(userId))
                .willReturn(BigDecimal.valueOf(3500.75));

        var resultActions = mockMvc
                .perform(get(requestMapping + "/highest-deposit/" + requestId, userId));

        resultActions.andDo(print())
                .andExpect(status().isOk());
    }

}
