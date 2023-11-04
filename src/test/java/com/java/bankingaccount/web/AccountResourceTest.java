package com.java.bankingaccount.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.java.bankingaccount.banking.account.dto.AccountDto;
import com.java.bankingaccount.banking.user.dto.UserDto;
import com.java.bankingaccount.banking.account.resource.AccountResource;
import com.java.bankingaccount.banking.account.service.AccountServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(AccountResource.class)
class AccountResourceTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private AccountServiceImpl accountService;
    @Autowired
    private ObjectMapper objectMapper;

    AccountDto accountDto;
    UserDto userDto;
    String requestMapping = "/api/v1/accounts/";
    String requestId = "/{id}";

    @BeforeEach
    void setUp(){
        userDto = UserDto.builder()
                .id(1)
                .firstName("Brook")
                .lastName("Oscar")
                .email("brook@gmail.com")
                .password("ManciiiiToto")
                .build();

        accountDto = AccountDto.builder()
                .id(1)
                .iban("FR7630001007941234567890185")
                .user(userDto)
                .build();
    }

    @Test
    void givenAccountDtoObject_whenCreateAccount_thenReturnAccountObject200() throws Exception {
        given(accountService.save(any(AccountDto.class)))
                .willReturn(accountDto);

        var resultActions = mockMvc
                .perform(post(requestMapping)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(accountDto))
                );

        resultActions.andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.iban", is(accountDto.getIban())));
    }

    @Test
    void givenAccountId_whenGetAccountId_thenReturn_200() throws Exception {
        given(accountService.getById(1))
                .willReturn(accountDto);

        var resultActions = mockMvc
                .perform(get(requestMapping + requestId, accountDto.getId()));

        resultActions.andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void givenNothing_whenGetAccountList_thenReturnAllAccount200() throws Exception {
        List<AccountDto> accountDtoList = new ArrayList<>();
        accountDtoList.add(accountDto);
        given(accountService.getAll())
                .willReturn(accountDtoList);

        var resultActions = mockMvc
                .perform(get(requestMapping));

        resultActions.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(accountDtoList.size())));
    }

    @Test
    void givenAccountId_whenDeleteAccount_thenReturn200() throws Exception {
        willDoNothing().given(accountService).delete(1);

        var resultActions = mockMvc
                .perform(delete(requestMapping + requestId, accountDto.getId()));

        resultActions.andDo(print())
                .andExpect(status().isAccepted());
    }
}
