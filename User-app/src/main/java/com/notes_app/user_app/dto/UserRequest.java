package com.notes_app.user_app.dto;

import lombok.Data;

@Data
public class UserRequest {

    private String name;
    private String email;
    private String password;

}
