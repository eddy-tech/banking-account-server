package com.java.bankingaccount.banking.address.service;

import com.java.bankingaccount.banking.address.dto.AddressDto;
import com.java.bankingaccount.banking.address.repository.AddressRepository;
import com.java.bankingaccount.core.validators.ObjectsValidator;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class AddressServiceImpl implements AddressService {
    private final AddressRepository addressRepository;
    private final ObjectsValidator<AddressDto> validator;

    @Override
    public AddressDto save(AddressDto addressDto) {
        validator.validate(addressDto);
        var address = AddressDto.toAddressDto(addressDto);

        log.info("Address saved with success");
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
                .orElseThrow(() -> new EntityNotFoundException("No address with id = " + id + " has not been found"));
    }

    @Override
    public void delete(Integer id) {
        getById(id);
        log.info("Address exist with this id = " + id);
        addressRepository.deleteById(id);

    }
}
