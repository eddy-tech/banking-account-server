package com.java.bankingaccount.services.impl;

import com.java.bankingaccount.dto.UserDto;
import com.java.bankingaccount.models.User;
import com.java.bankingaccount.repositories.UserRepository;
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
}
