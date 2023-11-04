package com.java.bankingaccount.banking.contact.repository;

import com.java.bankingaccount.banking.core.models.Contact;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContactRepository extends JpaRepository<Contact, Integer> {
    List<Contact> findAllByUserId(Integer userId);
}