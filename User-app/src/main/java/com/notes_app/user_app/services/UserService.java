package com.notes_app.user_app.services;

import com.notes_app.user_app.dto.UserRequest;
import org.springframework.stereotype.Service;

import com.notes_app.user_app.dto.UserResponse;
import com.notes_app.user_app.models.User;
import com.notes_app.user_app.repositories.UserRepository;


import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public List<UserResponse> fetchAllUsers() {
        return userRepository.findAll().stream().map(this::mapToUserResponse).collect(Collectors.toList());

        // List<User> users = userRepository.findAll();

        // List<UserResponse> resList = new ArrayList<>();
        // for (User currentUser : users) {
        // UserResponse currentUserResponse = mapToUserResponse(currentUser);
        // resList.add(currentUserResponse);

        // }

        // return resList;
    }

    public UserResponse fetchUserById(Long id){
        return userRepository.findById(id).map(this::mapToUserResponse).orElse(null);
    }

    public UserResponse addUser(UserRequest user) {
        User existingUser = userRepository.findByEmail(user.getEmail());

        // If there is already a User with this email
        if (existingUser != null) {
            return null;
        }

        User newUser = mapToUser(user);

        newUser = userRepository.save(newUser);

        return mapToUserResponse(newUser);
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