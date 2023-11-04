package com.java.bankingaccount.banking.address.repository;

import com.java.bankingaccount.banking.core.models.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Integer> {
    Address findAddressByStreet(String street);
}