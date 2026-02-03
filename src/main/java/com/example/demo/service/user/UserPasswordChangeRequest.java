package com.example.demo.service.user;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Setter
@Getter
public class UserPasswordChangeRequest {

    private String id;
    private UserRequest userRequest;
    private String passwordBefore;
    private String passwordAfter;
    private Instant changedAt;
}
