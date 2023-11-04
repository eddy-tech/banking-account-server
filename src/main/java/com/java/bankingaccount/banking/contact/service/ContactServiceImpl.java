package com.java.bankingaccount.banking.contact.service;

import com.java.bankingaccount.banking.contact.dto.ContactDto;
import com.java.bankingaccount.banking.contact.repository.ContactRepository;
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
public class ContactServiceImpl implements ContactService {
    private final ContactRepository contactRepository;
    private final ObjectsValidator<ContactDto> validator;
    @Override
    public ContactDto save(ContactDto contactDto) {
        validator.validate(contactDto);
        var contact = ContactDto.toContactDto(contactDto);
        log.info("saved account " + contact);
        return ContactDto.fromContact(contactRepository.save(contact));
    }

    @Override
    public List<ContactDto> getAll() {

        return contactRepository.findAll()
                .stream()
                .map(ContactDto::fromContact)
                .toList();
    }

    @Override
    public ContactDto getById(Integer id) {

        return contactRepository.findById(id)
                .map(ContactDto::fromContact)
                .orElseThrow(()-> new EntityNotFoundException("No contact with id = " + id + " has not been found"));
    }

    @Override
    public void delete(Integer id) {
        getById(id);
        log.info("Contact exist with this id = " + id);
        contactRepository.deleteById(id);
    }

    @Override
    public List<ContactDto> findAllByUserId(Integer userId) {
        return contactRepository.findAllByUserId(userId)
                .stream()
                .map(ContactDto::fromContact)
                .toList();
    }
}
