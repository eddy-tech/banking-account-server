package com.java.bankingaccount.services.impl;

import com.java.bankingaccount.dto.AddressDto;
import com.java.bankingaccount.repositories.AddressRepository;
import com.java.bankingaccount.services.AddressService;
import com.java.bankingaccount.validators.ObjectsValidator;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class AddressServiceImpl implements AddressService {
    private final AddressRepository addressRepository;
    private final ObjectsValidator<AddressDto> validator;

    @Override
    public AddressDto save(AddressDto addressDto) {
        validator.validate(addressDto);
        var address = AddressDto.toAddressDto(addressDto);

        return AddressDto.fromAddress(addressRepository.save(address));
    }

    @Override
    public List<AddressDto> getAll() {

        return addressRepository.findAll()
                .stream()
                .map(AddressDto::fromAddress)
                .toList();
    }

    @Override
    public AddressDto getById(Integer id) {

        return addressRepository.findById(id)
                .map(AddressDto::fromAddress)
                .orElseThrow(() -> new EntityNotFoundException("Address was not found with the id " + id));
    }

    @Override
    public void delete(Integer id) {
        addressRepository.deleteById(id);

    }
}
