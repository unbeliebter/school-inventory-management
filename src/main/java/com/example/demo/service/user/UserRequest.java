package com.example.demo.service.user;

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
    private RoleRequest role;
    private boolean changedPassword;
    private boolean requiresPasswordReset;
}
