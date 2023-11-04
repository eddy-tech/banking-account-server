package com.java.bankingaccount.banking.contact.resource;

import com.java.bankingaccount.banking.contact.dto.ContactDto;
import com.java.bankingaccount.banking.core.models.HttpResponse;
import com.java.bankingaccount.banking.contact.service.ContactService;
import com.java.bankingaccount.core.utils.RootEntPoint;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static com.java.bankingaccount.banking.contact.roots.ContactEndpoint.*;
import static com.java.bankingaccount.core.utils.RoleUtils.ADMIN;
import static com.java.bankingaccount.core.utils.RootEntPoint.ROOT_ENDPOINT;
import static org.springframework.http.HttpStatus.ACCEPTED;

@RestController
@RequestMapping( ROOT_ENDPOINT + CONTACT_ENDPOINT)
@RequiredArgsConstructor
public class ContactResource {
    private final ContactService contactService;

    @PostMapping
    @PreAuthorize(ADMIN)
    public ResponseEntity<HttpResponse> save(@RequestBody ContactDto dto) {
        var newContact = contactService.save(dto);
        return ResponseEntity.created(URI.create("")).body(
                HttpResponse.builder()
                        .timeStamp(LocalDateTime.now().toString())
                        .data(Map.of("Contact is connected", newContact))
                        .message("Contact is successfully created")
                        .status(HttpStatus.CREATED)
                        .statusCode(HttpStatus.CREATED.value())
                        .build()
        );
    }

    @GetMapping
    @PreAuthorize(ADMIN)
    public ResponseEntity<List<ContactDto>> getAll() {
        return ResponseEntity.ok(contactService.getAll());
    }

    @GetMapping(CONTACT_ID_ENDPOINT)
    @PreAuthorize(ADMIN)
    public ResponseEntity<HttpResponse> getById(@PathVariable(name = "contactId") Integer id) {
        var contact = contactService.getById(id);
        return ResponseEntity.ok().body(
                HttpResponse.builder()
                        .timeStamp(LocalDateTime.now().toString())
                        .data(Map.of("Contact ", contact))
                        .message("Contact information")
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .build()
        );
    }

    @GetMapping(CONTACT_USER_ENDPOINT)
    @PreAuthorize(ADMIN)
    public ResponseEntity<List<ContactDto>> findAllByUserId(@PathVariable(name = "userId") Integer userId) {
        return ResponseEntity.ok(contactService.findAllByUserId(userId));
    }

    @DeleteMapping(CONTACT_ID_ENDPOINT)
    @PreAuthorize(ADMIN)
    public ResponseEntity<HttpResponse> delete(@PathVariable(name = "contactId")Integer id) {
        contactService.delete(id);
        return ResponseEntity.accepted().body(
                HttpResponse.builder()
                        .timeStamp(LocalDateTime.now().toString())
                        .message("Contact deleted")
                        .status(ACCEPTED)
                        .statusCode(ACCEPTED.value())
                        .build()
        );
    }
}
