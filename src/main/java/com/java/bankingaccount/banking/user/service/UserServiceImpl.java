package com.java.bankingaccount.banking.user.service;

import com.java.bankingaccount.banking.core.auth.ChangePasswordRequest;
import com.java.bankingaccount.banking.account.dto.AccountDto;
import com.java.bankingaccount.banking.user.dto.UserDto;
import com.java.bankingaccount.banking.core.models.User;
import com.java.bankingaccount.banking.account.repository.AccountRepository;
import com.java.bankingaccount.banking.user.repository.UserRepository;
import com.java.bankingaccount.banking.account.service.AccountService;
import com.java.bankingaccount.core.validators.ObjectsValidator;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final AccountRepository accountRepository;
    private final AccountService accountService;
    private final ObjectsValidator<UserDto> validator;

    @Override
    public UserDto save(UserDto userDto) {
        validator.validate(userDto);
        var user = UserDto.toUserDto(userDto);
        user.setActive(true);
        log.info("User has been saved " + user);

        return UserDto.fromUser(userRepository.save(user));
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
                .orElseThrow(() -> new EntityNotFoundException("No user with id = " + id + " has not been found"));
    }

    @Override
    public void delete(Integer id) {
        getById(id);
        log.info("User exist with this id = " + id);
        userRepository.deleteById(id);
    }

    @Override
    public Integer validateAccount(Integer id) {
        var user = userRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException("No user was found with an account for validation"));

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
        var user = userRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException("No user was found for user account validation"));

        var existingAccount = accountRepository.findByUserId(user.getId())
                        .orElseThrow();

        if(existingAccount.getUser().isActive()){
            accountService.delete(existingAccount.getId());
        }

        user.setActive(false);
        userRepository.save(user);
        return user.getId();
    }

    @Override
    public void changePassword(ChangePasswordRequest request, Principal connectedUser) {
        var user = (User)((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();

        if(!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())){
            throw new IllegalStateException("Wrong Password");
        }

        if(!request.getNewPassword().equals(request.getConfirmationPassword())){
            throw new IllegalStateException("Both password are not the same");
        }

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);
    }
}
