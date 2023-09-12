package com.java.bankingaccount.dto;

import com.java.bankingaccount.models.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link com.java.bankingaccount.models.User}
 */
@Data
@AllArgsConstructor
@Builder
public class UserDto implements Serializable {
    private Integer id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;

    public static UserDto fromUser(User user) {
        return UserDto.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .password(user.getPassword())
                .build();
    }

    public static User toUserDto(UserDto user) {
        return User.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .password(user.getPassword())
                .build();
    }
}