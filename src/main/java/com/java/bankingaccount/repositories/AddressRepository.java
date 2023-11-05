package com.java.bankingaccount.repositories;

import com.java.bankingaccount.models.Address;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AddressRepository extends JpaRepository<Address, Integer> {
    Address findAddressByStreet(String street);
}