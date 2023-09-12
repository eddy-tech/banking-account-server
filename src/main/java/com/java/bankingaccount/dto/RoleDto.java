package com.java.bankingaccount.dto;

import com.java.bankingaccount.models.Role;
import com.java.bankingaccount.models.Transaction;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link com.java.bankingaccount.models.Role}
 */
@Data
@AllArgsConstructor
@Builder
public class RoleDto implements Serializable {
    private String name;

    public static RoleDto fromRole(Role role) {
        return RoleDto.builder()
                .name(role.getName())
                .build();
    }

    public static Role toRoleDto(RoleDto role) {
        return Role.builder()
                .name(role.getName())
                .build();
    }
}