package com.java.bankingaccount.banking.transaction.services;

import com.java.bankingaccount.banking.transaction.dto.TransactionDto;
import com.java.bankingaccount.banking.user.service.AbstractService;

import java.util.List;

public interface TransactionService extends AbstractService<TransactionDto> {
    List<TransactionDto> findAllByUser(Integer userId);
}
