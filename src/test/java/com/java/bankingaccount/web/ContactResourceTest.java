package com.java.bankingaccount.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.java.bankingaccount.banking.contact.dto.ContactDto;
import com.java.bankingaccount.banking.contact.resource.ContactResource;
import com.java.bankingaccount.banking.contact.service.ContactServiceImpl;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(ContactResource.class)
class ContactResourceTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ContactServiceImpl contactService;
    @Autowired
    private ObjectMapper objectMapper;
    ContactDto contactDto;
    String requestMapping = "/api/v1/contacts/";
    String requestId = "/{id}";

    @BeforeEach
    void setUp(){
        contactDto = ContactDto.builder()
                .id(1)
                .firstName("Brook")
                .lastName("Oscar")
                .email("brook@gmail.com")
                .iban("FR7630001007941234567890185")
                .userId(1)
                .build();
    }

    @Test
    void givenContactDtoObject_whenCreateContact_thenReturnContactObject202() throws Exception {
        given(contactService.save(any(ContactDto.class)))
                .willReturn(contactDto);

        var resultActions = mockMvc
                .perform(post(requestMapping)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(contactDto))
                );

        resultActions.andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName", is(contactDto.getFirstName())))
                .andExpect(jsonPath("$.lastName", is(contactDto.getLastName())))
                .andExpect(jsonPath("$.email", is(contactDto.getEmail())))
                .andExpect(jsonPath("$.userId", is(contactDto.getUserId())))
                .andExpect(jsonPath("$.iban", is(contactDto.getIban())));
    }

    @Test
    void givenContactId_whenGetContactId_thenReturn_200() throws Exception {
        given(contactService.getById(1))
                .willReturn(contactDto);

        var resultActions = mockMvc
                .perform(get(requestMapping + requestId, contactDto.getId()));

        resultActions.andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void givenNothing_whenGetContactList_thenReturnAllContact200() throws Exception {
        List<ContactDto> contactDtoList = new ArrayList<>();
        contactDtoList.add(contactDto);
        given(contactService.getAll())
                .willReturn(contactDtoList);

        var resultActions = mockMvc
                .perform(get(requestMapping));

        resultActions.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(contactDtoList.size())));
    }

  @Test
    void givenUserId_whenGetContactListUsers_thenReturnAllUsersContact200() throws Exception {
        List<ContactDto> contactDtoList = new ArrayList<>();
        contactDtoList.add(contactDto);
        given(contactService.findAllByUserId(1))
                .willReturn(contactDtoList);

        var resultActions = mockMvc
                .perform(get(requestMapping + "/users/" + requestId, contactDto.getUserId()));

        resultActions.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(contactDtoList.size())));
    }

    @Test
    void givenContactId_whenDeleteContact_thenReturn204() throws Exception {
        willDoNothing().given(contactService).delete(contactDto.getId());

        var resultActions = mockMvc
                .perform(delete(requestMapping + requestId, contactDto.getId()));

        resultActions.andDo(print())
                .andExpect(status().isAccepted());
    }
}
