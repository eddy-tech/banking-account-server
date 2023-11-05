package com.java.bankingaccount.integration.containers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.java.bankingaccount.dto.AccountDto;
import com.java.bankingaccount.integration.AbstractionContainerBaseTest;
import com.java.bankingaccount.models.Account;
import com.java.bankingaccount.models.Address;
import com.java.bankingaccount.models.User;
import com.java.bankingaccount.repositories.AccountRepository;
import com.java.bankingaccount.repositories.AddressRepository;
import com.java.bankingaccount.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Slf4j
class AccountResourceTestContainer  extends AbstractionContainerBaseTest {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private AddressRepository addressRepository;
    Account account;
    User user;
    Address address;
    String requestMapping = "/api/v1/accounts/";
    String requestId = "/{id}";

    @BeforeEach
    void setUp(){
        address = Address.builder()
                .street("rue sans martin")
                .city("Paris")
                .zipCode(75015)
                .country("France")
                .houseNumber(2)
                .createdDate(LocalDateTime.now())
                .build();
        addressRepository.save(address);

        user = User.builder()
                .firstName("Oumar")
                .lastName("césar")
                .email("oumar@gmail.com")
                .password("Groustafo25")
                .active(false)
                .address(address)
                .createdDate(LocalDateTime.now())
                .build();
        userRepository.save(user);

        account = Account.builder()
                .iban("FR7610107001011234567890129")
                .user(user)
                .createdDate(LocalDateTime.now())
                .build();

        accountRepository.save(account);
    }

    @AfterEach
    void tearDown(){
    }

    @Test
    void givenAccountDtoObject_whenCreateAccount_thenReturnAccountObject200() throws Exception {
        var resultActions = mockMvc
                .perform(post(requestMapping)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(account))
                );

        resultActions.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.iban", is(account.getIban())));
    }

    @Test
    void givenAccountId_whenGetAccountId_thenReturn_200() throws Exception {
        accountRepository.save(account);
        var resultActions = mockMvc
                .perform(get(requestMapping + requestId, account.getId()));

        resultActions.andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void givenNothing_whenGetAccountList_thenReturnAllAccount200() throws Exception {
        List<Account> accountList = new ArrayList<>();
        accountList.add(account);

        var resultActions = mockMvc
                .perform(get(requestMapping));

        resultActions.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(accountList.size())));
    }

    @Test
    void givenAccountId_whenDeleteAccount_thenReturn200() throws Exception {
        var resultActions = mockMvc
                .perform(delete(requestMapping + requestId, account.getId()));

        resultActions.andDo(print())
                .andExpect(status().isAccepted());
    }
}
