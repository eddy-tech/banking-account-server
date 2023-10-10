package com.java.bankingaccount.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.java.bankingaccount.dto.UserDto;
import com.java.bankingaccount.resources.UserResource;
import com.java.bankingaccount.services.impl.UserServiceImpl;
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
@WebMvcTest(UserResource.class)
class UserResourceTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UserServiceImpl userService;
    @Autowired
    private ObjectMapper objectMapper;
    UserDto userDto;
    String requestMapping = "/api/v1/users/";
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
    }

    @Test
    void givenUserDtoObject_whenCreateUser_thenReturnUserObject200() throws Exception {
        given(userService.save(any(UserDto.class)))
                .willReturn(userDto);

        var resultActions = mockMvc
                .perform(post(requestMapping)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDto))
                );

        resultActions.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", is(userDto.getFirstName())))
                .andExpect(jsonPath("$.lastName", is(userDto.getLastName())))
                .andExpect(jsonPath("$.email", is(userDto.getEmail())))
                .andExpect(jsonPath("$.password", is(userDto.getPassword())));
    }

    @Test
    void givenUserId_whenGetUserId_thenReturn_200() throws Exception {
        given(userService.getById(1))
                .willReturn(userDto);

        var resultActions = mockMvc
                .perform(get(requestMapping + requestId, userDto.getId()));

        resultActions.andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void givenNothing_whenGetUserList_thenReturnAllUser200() throws Exception {
        List<UserDto> userDtoList = new ArrayList<>();
        userDtoList.add(userDto);
        given(userService.getAll())
                .willReturn(userDtoList);

        var resultActions = mockMvc
                .perform(get(requestMapping));

        resultActions.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(userDtoList.size())));
    }

    @Test
    void givenUserId_whenDeleteUser_thenReturn200() throws Exception {
        willDoNothing().given(userService).delete(userDto.getId());

        var resultActions = mockMvc
                .perform(delete(requestMapping + requestId, userDto.getId()));

        resultActions.andDo(print())
                .andExpect(status().isAccepted());
    }

    @Test
    void givenUserId_whenValidateUser_thenReturnInteger() throws Exception {
        given(userService.validateAccount(1))
                .willReturn(1);

        var resultActions = mockMvc
                .perform(patch(requestMapping + "/validate/" + requestId, userDto.getId()));

        resultActions.andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void givenUserId_whenInvalidateUser_thenReturnInteger() throws Exception {
        given(userService.invalidateAccount(1))
                .willReturn(0);

        var resultActions = mockMvc
                .perform(patch(requestMapping + "/invalidate/" + requestId, userDto.getId()));

        resultActions.andDo(print())
                .andExpect(status().isOk());
    }
}
