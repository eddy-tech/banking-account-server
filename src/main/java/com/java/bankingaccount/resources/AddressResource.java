package com.java.bankingaccount.resources;

import com.java.bankingaccount.dto.AddressDto;
import com.java.bankingaccount.services.AddressService;
import com.java.bankingaccount.utils.RootEntPoint;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(RootEntPoint.ROOT_ENDPOINT + "/addresses")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
public class AddressResource {
    private final AddressService addressService;

    @PostMapping("/")
    @PreAuthorize("hasRole('ROLE_ADMIN') || hasRole('ROLE_USER')")
    public ResponseEntity<AddressDto> save(@RequestBody AddressDto dto) {
        return ResponseEntity.ok(addressService.save(dto));
    }

    @GetMapping("/")
    @PreAuthorize("hasRole('ROLE_ADMIN') || hasRole('ROLE_USER')")
    public ResponseEntity<List<AddressDto>> getAll() {
        return ResponseEntity.ok(addressService.getAll());
    }

    @GetMapping("/{addressId}")
    @PreAuthorize("hasRole('ROLE_ADMIN') || hasRole('ROLE_USER')")
    public ResponseEntity<AddressDto> getById(@PathVariable(name = "addressId") Integer id) {
        return ResponseEntity.ok(addressService.getById(id));
    }

    @DeleteMapping("/{addressId}")
    @PreAuthorize("hasRole('ROLE_ADMIN') || hasRole('ROLE_USER')")
    public ResponseEntity<Void> delete(@PathVariable(name = "addressId")Integer id) {
        addressService.delete(id);
        return ResponseEntity.accepted().build();
    }
}
