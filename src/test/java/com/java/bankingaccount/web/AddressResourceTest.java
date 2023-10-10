package com.java.bankingaccount.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.java.bankingaccount.dto.AccountDto;
import com.java.bankingaccount.dto.AddressDto;
import com.java.bankingaccount.dto.UserDto;
import com.java.bankingaccount.repositories.AccountRepository;
import com.java.bankingaccount.repositories.AddressRepository;
import com.java.bankingaccount.resources.AccountResource;
import com.java.bankingaccount.resources.AddressResource;
import com.java.bankingaccount.services.impl.AccountServiceImpl;
import com.java.bankingaccount.services.impl.AddressServiceImpl;
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

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(AddressResource.class)
class AddressResourceTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private AddressServiceImpl addressService;
    @Autowired
    private ObjectMapper objectMapper;
    AddressDto addressDto;
    String requestMapping = "/api/v1/addresses/";
    String requestId = "/{id}";

    @BeforeEach
    void setUp(){
        addressDto = AddressDto.builder()
                .id(1)
                .street("Rue du Mongo")
                .city("Paris")
                .zipCode(75012)
                .country("France")
                .houseNumber(12)
                .userId(2)
                .build();
    }

    @Test
    void givenAddressDtoObject_whenCreateAddress_thenReturnAddressObject200() throws Exception {
        given(addressService.save(any(AddressDto.class)))
                .willReturn(addressDto);

        var resultActions = mockMvc
                .perform(post(requestMapping)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(addressDto))
                );

        resultActions.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.street", is(addressDto.getStreet())))
                .andExpect(jsonPath("$.city", is(addressDto.getCity())))
                .andExpect(jsonPath("$.zipCode", is(addressDto.getZipCode())))
                .andExpect(jsonPath("$.country", is(addressDto.getCountry())))
                .andExpect(jsonPath("$.houseNumber", is(addressDto.getHouseNumber())))
                .andExpect(jsonPath("$.userId", is(addressDto.getUserId())));
    }

    @Test
    void givenAddressId_whenGetAddressId_thenReturn_200() throws Exception {
        given(addressService.getById(1))
                .willReturn(addressDto);

        var resultActions = mockMvc
                .perform(get(requestMapping + requestId, addressDto.getId()));

        resultActions.andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void givenNothing_whenGetAddressList_thenReturnAllAddress200() throws Exception {
        List<AddressDto> addressDtoList = new ArrayList<>();
        addressDtoList.add(addressDto);
        given(addressService.getAll())
                .willReturn(addressDtoList);

        var resultActions = mockMvc
                .perform(get(requestMapping));

        resultActions.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(addressDtoList.size())));
    }

    @Test
    void givenAddressId_whenDeleteAddress_thenReturn200() throws Exception {
        willDoNothing().given(addressService).delete(1);

        var resultActions = mockMvc
                .perform(delete(requestMapping + requestId, addressDto.getId()));

        resultActions.andDo(print())
                .andExpect(status().isAccepted());
    }
}
