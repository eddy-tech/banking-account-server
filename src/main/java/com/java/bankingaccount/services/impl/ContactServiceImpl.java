package com.java.bankingaccount.services.impl;

import com.java.bankingaccount.dto.ContactDto;
import com.java.bankingaccount.repositories.ContactRepository;
import com.java.bankingaccount.services.ContactService;
import com.java.bankingaccount.validators.ObjectsValidator;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Transactional
public class ContactServiceImpl implements ContactService {
    private final ContactRepository contactRepository;
    private final ObjectsValidator<ContactDto> validator;
    @Override
    public Integer save(ContactDto contactDto) {
        validator.validate(contactDto);
        var contact = ContactDto.toContactDto(contactDto);

        return contactRepository.save(contact).getId();
    }

    @Override
    public List<ContactDto> getAll() {

        return contactRepository.findAll()
                .stream()
                .map(ContactDto::fromContact)
                .collect(Collectors.toList());
    }

    @Override
    public ContactDto getById(Integer id) {

        return contactRepository.findById(id)
                .map(ContactDto::fromContact)
                .orElseThrow(()-> new EntityNotFoundException("Contact was ont found with the id " + id));
    }

    @Override
    public void delete(Integer id) {
        contactRepository.deleteById(id);

    }
}
