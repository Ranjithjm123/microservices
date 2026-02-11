package com.notes_app.user_app.services;

import com.notes_app.user_app.dto.UserRequest;
import org.springframework.stereotype.Service;

import com.notes_app.user_app.dto.UserResponse;
import com.notes_app.user_app.models.User;
import com.notes_app.user_app.repositories.UserRepository;


import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public List<UserResponse> fetchAllUsers() {
        return userRepository.findAll().stream()
                .map(this::mapToUserResponse)
                .collect(Collectors.toList());
    }

    public Optional<UserResponse> fetchUserById(Long id) {
        return userRepository.findById(id).map(this::mapToUserResponse);
    }

    public Optional<UserResponse> addUser(UserRequest userRequest) {
        // Check if email already exists
        if (userRepository.findByEmail(userRequest.getEmail()) != null) {
            return Optional.empty();
        }

        User newUser = mapToUser(userRequest);
        User savedUser = userRepository.save(newUser);
        return Optional.of(mapToUserResponse(savedUser));
    }

    private User mapToUser(UserRequest userRequest) {
        User user = new User();

        user.setName(userRequest.getName());
        user.setEmail(userRequest.getEmail());
        user.setPassword(userRequest.getPassword());

        return user;
    }

    private UserResponse mapToUserResponse(User user) {
        UserResponse userResponse = new UserResponse();

        userResponse.setId(user.getId());
        userResponse.setEmail(user.getEmail());
        userResponse.setName(user.getName());

        return userResponse;
    }
}