package com.java.bankingaccount.banking.core.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serial;
import java.io.Serializable;

@Data
@SuperBuilder
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Address extends AbstractEntity implements Serializable {
    private String street;
    private Integer houseNumber;
    private Integer zipCode;
    private String city;
    private String country;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
}
