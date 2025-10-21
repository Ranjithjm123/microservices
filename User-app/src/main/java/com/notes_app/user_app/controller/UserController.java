package com.notes_app.user_app.controller;

import java.util.List;

import com.notes_app.user_app.models.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.notes_app.user_app.dto.UserRequest;
import com.notes_app.user_app.dto.UserResponse;
import com.notes_app.user_app.services.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;

    @GetMapping("/all")
    public List<UserResponse> getAllUsers() {
        return userService.fetchAllUsers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Long id){
        UserResponse user = userService.fetchUserById(id);
        if(user!=null)
            return new ResponseEntity<>(user, HttpStatus.OK);

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/new")
    public ResponseEntity<UserResponse> createUser(@RequestBody UserRequest user) {
        UserResponse newUser = userService.addUser(user);

        if (newUser != null) {
            return new ResponseEntity<>(newUser, HttpStatus.CREATED);
        }

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

}