package com.java.bankingaccount.dto;

import com.java.bankingaccount.models.Contact;
import com.java.bankingaccount.models.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link com.java.bankingaccount.models.Contact}
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