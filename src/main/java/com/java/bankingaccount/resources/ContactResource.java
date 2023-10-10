package com.java.bankingaccount.resources;

import com.java.bankingaccount.dto.ContactDto;
import com.java.bankingaccount.services.ContactService;
import com.java.bankingaccount.utils.RootEntPoint;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(RootEntPoint.ROOT_ENDPOINT + "/contacts")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class ContactResource {
    private final ContactService contactService;

    @PostMapping("/")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ContactDto> save(@RequestBody ContactDto dto) {
        return ResponseEntity.ok(contactService.save(dto));
    }

    @GetMapping("/")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<ContactDto>> getAll() {
        return ResponseEntity.ok(contactService.getAll());
    }

    @GetMapping("/{contactId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ContactDto> getById(@PathVariable(name = "contactId") Integer id) {
        return ResponseEntity.ok(contactService.getById(id));
    }

    @GetMapping("/users/{userId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<ContactDto>> findAllByUserId(@PathVariable(name = "userId") Integer userId) {
        return ResponseEntity.ok(contactService.findAllByUserId(userId));
    }

    @DeleteMapping("/{contactId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable(name = "contactId")Integer id) {
        contactService.delete(id);
        return ResponseEntity.accepted().build();
    }
}
