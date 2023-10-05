package com.java.bankingaccount.services;

import com.java.bankingaccount.dto.ContactDto;

import java.util.List;

public interface ContactService extends AbstractService<ContactDto> {
    List<ContactDto> findAllByUserId(Integer userId);
}
