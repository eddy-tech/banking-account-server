package com.java.bankingaccount.banking.transaction.services;

import com.java.bankingaccount.banking.transaction.dto.TransactionDto;
import com.java.bankingaccount.banking.transaction.repository.TransactionRepository;
import com.java.bankingaccount.core.utils.TransactionType;
import com.java.bankingaccount.core.validators.ObjectsValidator;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class TransactionServiceImpl implements TransactionService {
    private  final TransactionRepository transactionRepository;
    private final ObjectsValidator<TransactionDto> validator;

    @Override
    public TransactionDto save(TransactionDto transactionDto) {
        validator.validate(transactionDto);
        var transaction = TransactionDto.toTransactionDto(transactionDto);
        var transactionMultiplier = getTransactionType(transaction.getType());
        var amount = transaction.getAmount().multiply(BigDecimal.valueOf(transactionMultiplier));
        transaction.setAmount(amount);
        log.info("saved transaction " + transaction);

        return TransactionDto.fromTransaction(transactionRepository.save(transaction));
    }

    @Override
    public List<TransactionDto> getAll() {
        return transactionRepository.findAll()
                .stream()
                .map(TransactionDto::fromTransaction)
                .toList();
    }

    @Override
    public TransactionDto getById(Integer id) {
        return transactionRepository.findById(id)
                .map(TransactionDto::fromTransaction)
                .orElseThrow(()-> new EntityNotFoundException("No transaction with id = " + id + " has not been found"));
    }

    @Override
    public void delete(Integer id) {
        getById(id);
        log.info("Transaction exist with this id = " + id);
        transactionRepository.deleteById(id);
    }

    private int getTransactionType(TransactionType type){
        return TransactionType.DEPOSIT == type ? 1 : -1;
    }

    @Override
    public List<TransactionDto> findAllByUser(Integer userId) {
        return transactionRepository.findAllByUserId(userId)
                .stream()
                .map(TransactionDto::fromTransaction)
                .toList();
    }
}
