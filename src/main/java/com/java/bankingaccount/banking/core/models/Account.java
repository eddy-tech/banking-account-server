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
public class Account extends AbstractEntity implements Serializable {
    private String iban;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
}
