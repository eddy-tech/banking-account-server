package com.java.bankingaccount.banking.contact.dto;

import com.java.bankingaccount.banking.core.models.Contact;
import com.java.bankingaccount.banking.core.models.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * DTO for {@link Contact}
 */
@Data
@AllArgsConstructor
@Builder
public class ContactDto implements Serializable {
    private Integer id;
    private String firstName;
    private String lastName;
    private String email;
    private String iban;
    private Integer userId;

    public static ContactDto fromContact(Contact contact){
        return ContactDto.builder()
                .id(contact.getId())
                .firstName(contact.getFirstName())
                .lastName(contact.getLastName())
                .email(contact.getEmail())
                .iban(contact.getIban())
                .userId(contact.getUser().getId())
                .build();
    }

    public static Contact toContactDto (ContactDto contact){
        return Contact.builder()
                .firstName(contact.getFirstName())
                .lastName(contact.getLastName())
                .email(contact.getEmail())
                .iban(contact.getIban())
                .user(User.builder().id(contact.getUserId()).build())
                .build();
    }
}