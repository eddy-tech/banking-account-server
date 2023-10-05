package com.java.bankingaccount.resources;

import com.java.bankingaccount.dto.AddressDto;
import com.java.bankingaccount.services.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/addresses")
@RequiredArgsConstructor
public class AddressResource {
    private final AddressService addressService;

    @PostMapping("/")
    public ResponseEntity<Integer> save(@RequestBody AddressDto dto) {
        return ResponseEntity.ok(addressService.save(dto));
    }

    @GetMapping("/")
    public ResponseEntity<List<AddressDto>> getAll() {
        return ResponseEntity.ok(addressService.getAll());
    }

    @GetMapping("/{addressId}")
    public ResponseEntity<AddressDto> getById(@PathVariable(name = "addressId") Integer id) {
        return ResponseEntity.ok(addressService.getById(id));
    }

    @DeleteMapping("/{addressId}")
    public ResponseEntity<Void> delete(@PathVariable(name = "addressId")Integer id) {
        addressService.delete(id);
        return ResponseEntity.accepted().build();
    }
}
