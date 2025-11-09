package com.example.demo.service.user;

import com.example.demo.entities.user.UserType;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserRequest {

    private String id;
    private String username;
    private String password;
    private String email;
    private String firstName;
    private String lastName;
    private UserType userType;
}
