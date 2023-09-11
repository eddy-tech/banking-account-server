package com.java.bankingaccount.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@SuperBuilder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "_users")
public class User extends AbstractEntity{
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private boolean active;

    @OneToOne
    private Address address;
    @OneToMany(mappedBy = "user")
    private List<Transaction> transactionList;
    @OneToMany(mappedBy = "user")
    private List<Contact> contactList;
    @OneToOne
    private Account account;
    @OneToOne
    private Role role;

}
