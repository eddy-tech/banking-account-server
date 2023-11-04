package com.java.bankingaccount.banking.user.dto;

import com.java.bankingaccount.core.enums.Roles;
import com.java.bankingaccount.banking.core.models.User;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * DTO for {@link User}
 */
@Data
@AllArgsConstructor
@Builder
public class UserDto implements Serializable {
    private Integer id;

    @NotNull(message = "The firstname must not be empty")
    @NotEmpty(message = "The firstname must not be empty")
    @NotBlank(message = "The firstname must not be empty")
    private String firstName;
    @NotNull(message = "The lastname must not be empty")
    @NotEmpty(message = "The lastname must not be empty")
    @NotBlank(message = "The lastname must not be empty")
    private String lastName;
    @NotNull(message = "The email must not be empty")
    @NotEmpty(message = "The email must not be empty")
    @NotBlank(message = "The email must not be empty")
    @Email(message = "This email does not the right email")
    private String email;
    @NotNull(message = "The password must not be empty")
    @NotEmpty(message = "The password must not be empty")
    @NotBlank(message = "The password must not be empty")
    @Size(min = 8, max = 16, message = "The password must be between 8 and 16 characters")
    private String password;
    private Roles roles;

    public static UserDto fromUser(User user) {
        return UserDto.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .password(user.getPassword())
                .roles(user.getRoles())
                .build();
    }

    public static User toUserDto(UserDto user) {
        return User.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .password(user.getPassword())
                .roles(user.getRoles())
                .build();
    }
}