package com.java.bankingaccount.banking.core.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

@Data
@SuperBuilder
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Contact extends AbstractEntity implements Serializable {
    private String firstName;
    private String lastName;
    @Column(unique = true)
    private String email;
    private String iban;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
