package com.java.bankingaccount.services.impl;

import com.java.bankingaccount.dto.TransactionDto;
import com.java.bankingaccount.repositories.TransactionRepository;
import com.java.bankingaccount.services.TransactionService;
import com.java.bankingaccount.utils.TransactionType;
import com.java.bankingaccount.validators.ObjectsValidator;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class TransactionServiceImpl implements TransactionService {
    private  final TransactionRepository transactionRepository;
    private final ObjectsValidator<TransactionDto> validator;

    @Override
    public Integer save(TransactionDto transactionDto) {
        validator.validate(transactionDto);
        var transaction = TransactionDto.toTransactionDto(transactionDto);
        var transactionMultiplier = getTransactionType(transaction.getType());
        var amount = transaction.getAmount().multiply(BigDecimal.valueOf(transactionMultiplier));
        transaction.setAmount(amount);

        return transactionRepository.save(transaction).getId();
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
                .orElseThrow(()-> new EntityNotFoundException("No transaction was found with the id " + id));
    }

    @Override
    public void delete(Integer id) {
        transactionRepository.deleteById(id);
    }

    private int getTransactionType(TransactionType type){
        return TransactionType.TRANSFER == type ? 1 : -1;
    }

    @Override
    public List<TransactionDto> findAllByUser(Integer userId) {
        return transactionRepository.findAllByUserId(userId)
                .stream()
                .map(TransactionDto::fromTransaction)
                .toList();
    }
}
