package com.java.bankingaccount.core.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public enum Roles {
    MANAGER(Collections.emptySet()),

    ADMIN(
            Set.of(
                    Permission.ADMIN_READ, Permission.ADMIN_CREATE, Permission.ADMIN_DELETE, Permission.ADMIN_UPDATE,
                    Permission.USER_CREATE, Permission.USER_DELETE, Permission.USER_UPDATE, Permission.USER_READ
            )
    )
    ,

    USER(
            Set.of(
                    Permission.USER_CREATE, Permission.USER_DELETE, Permission.USER_UPDATE, Permission.USER_READ
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