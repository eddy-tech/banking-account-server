package com.java.bankingaccount.banking.address.resource;

import com.java.bankingaccount.banking.address.dto.AddressDto;
import com.java.bankingaccount.banking.core.models.HttpResponse;
import com.java.bankingaccount.banking.address.service.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static com.java.bankingaccount.banking.address.roots.AddressEndpoint.ADDRESS_ENDPOINT;
import static com.java.bankingaccount.banking.address.roots.AddressEndpoint.ADDRESS_ID_ENDPOINT;
import static com.java.bankingaccount.core.utils.RoleUtils.ADMIN_USER;
import static com.java.bankingaccount.core.utils.RootEntPoint.ROOT_ENDPOINT;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.HttpStatus.ACCEPTED;

@RestController
@RequestMapping(ROOT_ENDPOINT + ADDRESS_ENDPOINT)
@RequiredArgsConstructor
public class AddressResource {
    private final AddressService addressService;

    @PostMapping
    @PreAuthorize(ADMIN_USER)
    public ResponseEntity<HttpResponse> save(@RequestBody AddressDto dto) {
        var newAddress = addressService.save(dto);
        return ResponseEntity.created(URI.create("")).body(
                HttpResponse.builder()
                        .timeStamp(LocalDateTime.now().toString())
                        .data(Map.of("new address", newAddress))
                        .message("Address created")
                        .status(CREATED)
                        .statusCode(CREATED.value())
                        .build()
        );
    }

    @GetMapping
    @PreAuthorize(ADMIN_USER)
    public ResponseEntity<List<AddressDto>> getAll() {
        return ResponseEntity.ok(addressService.getAll());
    }

    @GetMapping(ADDRESS_ID_ENDPOINT)
    @PreAuthorize(ADMIN_USER)
    public ResponseEntity<HttpResponse> getById(@PathVariable(name = "addressId") Integer id) {
        var address = addressService.getById(id);
        return ResponseEntity.ok().body(
                HttpResponse.builder()
                        .timeStamp(LocalDateTime.now().toString())
                        .data(Map.of("address", address))
                        .message("address information")
                        .status(OK)
                        .statusCode(OK.value())
                        .build()
        );
    }

    @DeleteMapping(ADDRESS_ID_ENDPOINT)
    @PreAuthorize(ADMIN_USER)
    public ResponseEntity<HttpResponse> delete(@PathVariable(name = "addressId")Integer id) {
        addressService.delete(id);
        return ResponseEntity.accepted().body(
                HttpResponse.builder()
                        .timeStamp(LocalDateTime.now().toString())
                        .message("Address deleted")
                        .status(ACCEPTED)
                        .statusCode(ACCEPTED.value())
                        .build()
        );
    }
}
