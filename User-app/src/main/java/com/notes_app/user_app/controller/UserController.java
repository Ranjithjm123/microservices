package com.notes_app.user_app.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.notes_app.user_app.dto.UserRequest;
import com.notes_app.user_app.dto.UserResponse;
import com.notes_app.user_app.services.UserService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;

    @GetMapping
    public ResponseEntity<?> getAllUsers() {
        return ResponseEntity.ok(userService.fetchAllUsers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Long id) {
        return userService.fetchUserById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<UserResponse> createUser(@Valid @RequestBody UserRequest user) {
        return userService.addUser(user)
                .map(newUser -> ResponseEntity.status(HttpStatus.CREATED).body(newUser))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.CONFLICT).build());
    }

}