package com.java.bankingaccount.services.impl;

import com.java.bankingaccount.dto.AccountDto;
import com.java.bankingaccount.dto.UserDto;
import com.java.bankingaccount.repositories.UserRepository;
import com.java.bankingaccount.services.AccountService;
import com.java.bankingaccount.services.UserService;
import com.java.bankingaccount.validators.ObjectsValidator;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final AccountService accountService;
    private final ObjectsValidator<UserDto> validator;

    @Override
    public Integer save(UserDto userDto) {
        validator.validate(userDto);
        var user = UserDto.toUserDto(userDto);

        return userRepository.save(user).getId();
    }

    @Override
    public List<UserDto> getAll() {
        return userRepository.findAll()
                .stream()
                .map(UserDto::fromUser)
                .toList();
    }

    @Override
    public UserDto getById(Integer id) {
        return userRepository.findById(id)
                .map(UserDto::fromUser)
                .orElseThrow(() -> new EntityNotFoundException("No user was found with the provided id : " + id));
    }

    @Override
    public void delete(Integer id) {
        //todo check before delete
        userRepository.deleteById(id);
    }

    @Override
    public Integer validateAccount(Integer id) {
        var user = userRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("No user was found for user account validation"));
        var account = AccountDto.builder()
                .user(UserDto.fromUser(user))
                .build();
         accountService.save(account);
         user.setActive(true);
         userRepository.save(user);
         return user.getId();
    }

    @Override
    public Integer invalidateAccount(Integer id) {
        var user = userRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("No user was found for user account validation"));
        user.setActive(false);
        userRepository.save(user);
        return user.getId();
    }
}
