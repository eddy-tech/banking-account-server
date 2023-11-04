package com.java.bankingaccount.banking.core.models;

import com.java.bankingaccount.banking.core.models.*;
import com.java.bankingaccount.banking.core.token.AccessToken;
import com.java.bankingaccount.core.enums.Roles;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Data
@SuperBuilder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "_users")
public class User extends AbstractEntity implements UserDetails {
    private String firstName;
    private String lastName;
    @Column(unique = true)
    private String email;
    private String password;
    private boolean active;

    @OneToOne
    private Address address;
    @OneToMany(mappedBy = "user")
    private List<Transaction> transactionList;
    @OneToMany(mappedBy = "user")
    private List<Contact> contactList;
    @OneToMany(mappedBy = "user")
    private List<AccessToken> accessTokenList;
    @OneToOne
    private Account account;
    @Enumerated(EnumType.STRING)
    private Roles roles;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.getAuthorities();
    }

    @Override
    public String getUsername() { return email; }
    @Override
    public String getPassword() { return password; }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return active;
    }
}
