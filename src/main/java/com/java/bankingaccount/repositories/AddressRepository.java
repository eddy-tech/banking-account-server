package com.java.bankingaccount.repositories;

import com.java.bankingaccount.models.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Integer> {
}