package com.java.bankingaccount.services.impl;

import com.java.bankingaccount.dto.AccountDto;
import com.java.bankingaccount.exceptions.OperationNoPermittedException;
import com.java.bankingaccount.models.Account;
import com.java.bankingaccount.repositories.AccountRepository;
import com.java.bankingaccount.services.AccountService;
import com.java.bankingaccount.validators.ObjectsValidator;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.iban4j.CountryCode;
import org.iban4j.Iban;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;
    private final ObjectsValidator<AccountDto> validator;

    @Override
    public Integer save(AccountDto accountDto) {
        if(accountDto.getId() != null){
            throw new OperationNoPermittedException(
                    "Cannot be updated", "save Account", "Account", "Updated not permitted"
            );
        }
        validator.validate(accountDto);
        Account account = AccountDto.toAccountDto(accountDto);
        var isUserAlreadyAccount = accountRepository.findByUserId(account.getId()).isPresent();
        if(isUserAlreadyAccount){
            throw new OperationNoPermittedException(
                "The selected user has already an active account",
                "Create account",
                "Account service",
                "Account creating"
            );
        }
        account.setIban(generateRandomIban());

        return accountRepository.save(account).getId();
    }

    @Override
    public List<AccountDto> getAll() {
        return accountRepository.findAll().stream()
                .map(AccountDto::fromAccount)
                .collect(Collectors.toList());
    }

    @Override
    public AccountDto getById(Integer id) {
        return accountRepository.findById(id)
                .map(AccountDto::fromAccount)
                .orElseThrow(()-> new EntityNotFoundException(("No account was found with the id: " + id)));
    }

    @Override
    public void delete(Integer id) {
        //todo check delete account
        accountRepository.deleteById(id);

    }

    private String generateRandomIban(){
        String iban = Iban.random(CountryCode.DE).toFormattedString();
        boolean existIban = accountRepository.findByIban(iban).isPresent();
        if(existIban){
            generateRandomIban();
        }
        return iban;
    }
}
