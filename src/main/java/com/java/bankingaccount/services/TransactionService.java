package com.java.bankingaccount.services;

import com.java.bankingaccount.dto.TransactionDto;

import java.util.List;

public interface TransactionService extends AbstractService<TransactionDto> {
    List<TransactionDto> findAllByUser(Integer userId);
}
