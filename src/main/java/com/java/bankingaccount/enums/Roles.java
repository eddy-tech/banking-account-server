package com.java.bankingaccount.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.java.bankingaccount.enums.Permission.*;

@RequiredArgsConstructor
public enum Roles {
    MANAGER(Collections.emptySet()),

    ADMIN(
            Set.of(
                    ADMIN_READ, ADMIN_CREATE, ADMIN_DELETE, ADMIN_UPDATE,
                    USER_CREATE, USER_DELETE, USER_UPDATE, USER_READ
            )
    )
    ,

    USER(
            Set.of(
                    USER_CREATE, USER_DELETE, USER_UPDATE, USER_READ
            )
    );

    @Getter
    private final Set<Permission> permissions;

    public List<SimpleGrantedAuthority> getAuthorities(){
        List<SimpleGrantedAuthority> authorities = getPermissions()
                .stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toList());
        System.out.println("First-authorities" + authorities);

        authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        System.out.println("second-authorities" + authorities);
        return authorities;
    }
}
