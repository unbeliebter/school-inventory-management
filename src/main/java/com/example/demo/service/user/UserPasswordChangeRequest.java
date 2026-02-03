package com.example.demo.service.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserPasswordChangeRequest {

    private String id;
    private UserRequest userRequest;
    private String passwordBefore;
    private String passwordAfter;
    private Instant changedAt;
}
