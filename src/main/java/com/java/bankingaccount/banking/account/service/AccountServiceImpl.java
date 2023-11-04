package com.java.bankingaccount.banking.account.service;

import com.java.bankingaccount.banking.account.dto.AccountDto;
import com.java.bankingaccount.core.exceptions.OperationNoPermittedException;
import com.java.bankingaccount.banking.core.models.Account;
import com.java.bankingaccount.banking.account.repository.AccountRepository;
import com.java.bankingaccount.core.validators.ObjectsValidator;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.iban4j.CountryCode;
import org.iban4j.Iban;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;
    private final ObjectsValidator<AccountDto> validator;

    @Override
    public AccountDto save(AccountDto accountDto) {
        validator.validate(accountDto);
        Account account = AccountDto.toAccountDto(accountDto);
        var userHasAlreadyAccount = accountRepository.findByUserId(account.getUser().getId());
        if(userHasAlreadyAccount.isPresent() && account.getUser().isActive()){
            throw new OperationNoPermittedException(
                "The selected user has already an active account",
                "Create account",
                "Account service",
                "Account creating"
            );
        }
        if(accountDto.getId() == null){
            account.setIban(generateRandomIban());
        }

        var accountSave = accountRepository.save(account);
        log.info("saved account " + accountSave);
        return AccountDto.fromAccount(accountSave);
    }

    @Override
    public List<AccountDto> getAll() {
        return accountRepository.findAll().stream()
                .map(AccountDto::fromAccount)
                .toList();
    }

    @Override
    public AccountDto getById(Integer id) {
        return accountRepository.findById(id)
                .map(AccountDto::fromAccount)
                .orElseThrow(()-> new EntityNotFoundException(("No account with id = " + id + " has not been found")));
    }

    @Override
    public void delete(Integer id) {
        getById(id);
        log.info("Account exist with this id = " + id);
        accountRepository.deleteById(id);
    }

    private String generateRandomIban(){
        String iban = Iban.random(CountryCode.DE).toFormattedString();
        boolean existIban = accountRepository.findByIban(iban).isPresent();
        if(existIban){
            generateRandomIban();
        }
        log.info("Iban has been created " + iban);
        return iban;
    }
}
