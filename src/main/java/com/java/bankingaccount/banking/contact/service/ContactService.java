package com.java.bankingaccount.banking.contact.service;

import com.java.bankingaccount.banking.contact.dto.ContactDto;
import com.java.bankingaccount.banking.user.service.AbstractService;

import java.util.List;

public interface ContactService extends AbstractService<ContactDto> {
    List<ContactDto> findAllByUserId(Integer userId);
}
