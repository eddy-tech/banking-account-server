package com.java.bankingaccount.banking.account.dto;

import com.java.bankingaccount.banking.user.dto.UserDto;
import com.java.bankingaccount.banking.core.models.Account;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

import static com.java.bankingaccount.banking.user.dto.UserDto.fromUser;
import static com.java.bankingaccount.banking.user.dto.UserDto.toUserDto;

/**
 * DTO for {@link Account}
 */
@Data
@AllArgsConstructor
@Builder
public class AccountDto implements Serializable {
    private Integer id;
    private String iban;
    private UserDto user;


    public static AccountDto fromAccount(Account account){
        return AccountDto.builder()
                .id(account.getId())
                .iban(account.getIban())
                .user(fromUser(account.getUser()))
                .build();
    }

    public static Account toAccountDto(AccountDto account){
        return Account.builder()
                .id(account.getId())
                .iban(account.getIban())
                .user(toUserDto(account.getUser()))
                .build();
    }
}