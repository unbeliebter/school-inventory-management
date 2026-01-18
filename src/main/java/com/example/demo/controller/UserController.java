package com.example.demo.controller;

import com.example.demo.entities.user.UserEntity;
import com.example.demo.service.user.UserRequest;
import com.example.demo.service.user.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("users/")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private AuthenticationManager authManager;

    @GetMapping("getAll")
    @PreAuthorize("hasRole('ADMIN')")
    public List<UserEntity> getAll(){
        return userService.getAll();
    }

    @GetMapping("get/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public UserEntity getById(@PathVariable String id){
        return userService.getById(id);
    }

    @PostMapping("create")
    @PreAuthorize("hasRole('ADMIN')")
    public UserEntity save(@RequestBody UserRequest request){
        return userService.create(request);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Credentials loginRequest) {

        try {
            Authentication auth = authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.username(), loginRequest.password())
            );
            SecurityContextHolder.getContext().setAuthentication(auth);
            return ResponseEntity.ok("Login successful");
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }

    @DeleteMapping("delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void delete(@PathVariable String id){
        userService.deleteById(id);
    }

    public record Credentials(String username, String password) {
    }
}
