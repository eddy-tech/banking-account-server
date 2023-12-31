package com.java.bankingaccount.banking.address.dto;

import com.java.bankingaccount.banking.core.models.Address;
import com.java.bankingaccount.banking.core.models.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * DTO for {@link Address}
 */
@Data
@AllArgsConstructor
@Builder
public class AddressDto implements Serializable {
    private Integer id;
    private String street;
    private Integer houseNumber;
    private Integer zipCode;
    private String city;
    private String country;
    private Integer userId;

    public static AddressDto fromAddress(Address address) {
        return AddressDto.builder()
                .id(address.getId())
                .street(address.getStreet())
                .houseNumber(address.getHouseNumber())
                .city(address.getCity())
                .zipCode(address.getZipCode())
                .country(address.getCountry())
                .userId(address.getUser().getId())
                .build();
    }

    public static Address toAddressDto(AddressDto address) {
        return Address.builder()
                .id(address.getId())
                .street(address.getStreet())
                .houseNumber(address.getHouseNumber())
                .city(address.getCity())
                .zipCode(address.getZipCode())
                .country(address.getCountry())
                .user(User.builder()
                        .id(address.getId())
                        .build()
                )
                .build();
    }
}