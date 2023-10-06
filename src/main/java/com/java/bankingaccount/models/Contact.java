package com.java.bankingaccount.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Contact extends AbstractEntity {
    private String firstName;
    private String lastName;
    private String email;
    private String iban;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
