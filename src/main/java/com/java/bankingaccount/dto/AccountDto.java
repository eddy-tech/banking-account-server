package com.java.bankingaccount.dto;

import com.java.bankingaccount.models.Account;
import com.java.bankingaccount.models.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Value;

import java.io.Serializable;

import static com.java.bankingaccount.dto.UserDto.fromUser;
import static com.java.bankingaccount.dto.UserDto.toUserDto;

/**
 * DTO for {@link com.java.bankingaccount.models.Account}
 */
@Data
@AllArgsConstructor
@Builder
public class AccountDto implements Serializable {
    private Integer id;
    private String iban;
    private UserDto user;


    private AccountDto fromAccount(Account account){
        return AccountDto.builder()
                .id(account.getId())
                .iban(account.getIban())
                .user(fromUser(account.getUser()))
                .build();
    }

    private Account toAccountDto(AccountDto account){
        return Account.builder()
                .id(account.getId())
                .iban(account.getIban())
                .user(toUserDto(account.getUser()))
                .build();
    }
}