package com.java.bankingaccount.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.java.bankingaccount.dto.TransactionDto;
import com.java.bankingaccount.resources.TransactionResource;
import com.java.bankingaccount.services.impl.TransactionServiceImpl;
import com.java.bankingaccount.utils.TransactionType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasValue;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(TransactionResource.class)
class TransactionResourceTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private TransactionServiceImpl transactionService;
    @Autowired
    private ObjectMapper objectMapper;
    TransactionDto transactionDto;
    String requestMapping = "/api/v1/transactions/";
    String requestId = "/{id}";

    @BeforeEach
    void setUp(){
        transactionDto = TransactionDto.builder()
                .id(1)
                .amount(BigDecimal.valueOf(5200.25))
                .type(TransactionType.TRANSFER)
                .destinationIban("FR7630001007941234567890185")
                .transactionDate(LocalDate.now())
                .userId(1)
                .build();
    }

    @Test
    void givenTransactionDtoObject_whenCreateContact_thenReturnTransactionObject200() throws Exception {
        given(transactionService.save(any(TransactionDto.class)))
                .willReturn(transactionDto);

        var resultActions = mockMvc
                .perform(post(requestMapping)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(transactionDto))
                );

        resultActions.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.amount", is(Double.valueOf(String.valueOf(transactionDto.getAmount())))))
                .andExpect(jsonPath("$.type", is(String.valueOf(transactionDto.getType()))))
                .andExpect(jsonPath("$.destinationIban", is(transactionDto.getDestinationIban())))
                .andExpect(jsonPath("$.userId", is(transactionDto.getUserId())))
                .andExpect(jsonPath("$.transactionDate", is(String.valueOf(transactionDto.getTransactionDate()))));
    }

    @Test
    void givenTransactionId_whenGetTransactionId_thenReturn_200() throws Exception {
        given(transactionService.getById(transactionDto.getId()))
                .willReturn(transactionDto);

        var resultActions = mockMvc
                .perform(get(requestMapping + requestId, transactionDto.getId()));

        resultActions.andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void givenNothing_whenGetTransactionList_thenReturnAllTransaction200() throws Exception {
        List<TransactionDto> transactionDtoList = new ArrayList<>();
        transactionDtoList.add(transactionDto);
        given(transactionService.getAll())
                .willReturn(transactionDtoList);

        var resultActions = mockMvc
                .perform(get(requestMapping));

        resultActions.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(transactionDtoList.size())));
    }

  @Test
    void givenUserId_whenGetTransactionListUsers_thenReturnAllUsersTransaction200() throws Exception {
        List<TransactionDto> transactionDtoList = new ArrayList<>();
        transactionDtoList.add(transactionDto);
        given(transactionService.findAllByUser(transactionDto.getUserId()))
                .willReturn(transactionDtoList);

        var resultActions = mockMvc
                .perform(get(requestMapping + "/users/" + requestId, transactionDto.getUserId()));

        resultActions.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(transactionDtoList.size())));
    }

    @Test
    void givenTransactionId_whenDeleteTransaction_thenReturn200() throws Exception {
        willDoNothing().given(transactionService).delete(transactionDto.getId());

        var resultActions = mockMvc
                .perform(delete(requestMapping + requestId, transactionDto.getId()));

        resultActions.andDo(print())
                .andExpect(status().isAccepted());
    }
}
