package com.java.bankingaccount.resources;

import com.java.bankingaccount.dto.ContactDto;
import com.java.bankingaccount.services.ContactService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/contacts")
@RequiredArgsConstructor
public class ContactResource {
    private final ContactService contactService;

    @PostMapping("/")
    public ResponseEntity<Integer> save(@RequestBody ContactDto dto) {
        return ResponseEntity.ok(contactService.save(dto));
    }

    @GetMapping("/")
    public ResponseEntity<List<ContactDto>> getAll() {
        return ResponseEntity.ok(contactService.getAll());
    }

    @GetMapping("/{contactId}")
    public ResponseEntity<ContactDto> getById(@PathVariable(name = "contactId") Integer id) {
        return ResponseEntity.ok(contactService.getById(id));
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<List<ContactDto>> findAllByUserId(@PathVariable(name = "userId") Integer userId) {
        return ResponseEntity.ok(contactService.findAllByUserId(userId));
    }

    @DeleteMapping("/{contactId}")
    public ResponseEntity<Void> delete(@PathVariable(name = "contactId")Integer id) {
        contactService.delete(id);
        return ResponseEntity.accepted().build();
    }
}
