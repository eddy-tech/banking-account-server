package com.java.bankingaccount.resources;

import com.java.bankingaccount.dto.UserDto;
import com.java.bankingaccount.services.UserService;
import com.java.bankingaccount.utils.RootEntPoint;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(RootEntPoint.ROOT_ENDPOINT + "/users")
@RequiredArgsConstructor
public class UserResource {
    private final UserService userService;

    @PostMapping("/")
    public ResponseEntity<UserDto> save(@RequestBody UserDto dto) {
        return ResponseEntity.ok(userService.save(dto));
    }

    @GetMapping("/")
    public ResponseEntity<List<UserDto>> getAll() {
        return ResponseEntity.ok(userService.getAll());
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getById(@PathVariable("userId")Integer id) {
        return ResponseEntity.ok(userService.getById(id));
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> delete(@PathVariable("userId") Integer id) {
        userService.delete(id);
        return ResponseEntity.accepted().build();
    }

    @PatchMapping("/validate/{userId}")
    public ResponseEntity<Integer> validateAccount(@PathVariable("userId")Integer id) {
        return ResponseEntity.ok(userService.validateAccount(id));
    }

    @PatchMapping("/invalidate/{userId}")
    public ResponseEntity<Integer> invalidateAccount(@PathVariable("userId")Integer id) {
        return ResponseEntity.ok(userService.invalidateAccount(id));
    }
}
