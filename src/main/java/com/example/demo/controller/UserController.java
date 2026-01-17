package com.example.demo.controller;

import com.example.demo.entities.user.UserEntity;
import com.example.demo.entities.user.UserType;
import com.example.demo.service.user.UserRequest;
import com.example.demo.service.user.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("users/")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("getAll")
    public List<UserEntity> getAll(){
        return userService.getAll();
    }

    @GetMapping("get/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public UserEntity getById(@PathVariable String id){
        return userService.getById(id);
    }

    @PostMapping("create")
    public UserEntity save(@RequestBody UserRequest request){
        return userService.create(request);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Credentials loginRequest) {

        boolean isAuthenticated = userService.authenticate(
                loginRequest.username(),
                loginRequest.password()
        );

        if (isAuthenticated) {
            // JWT/Session-Token??
            return ResponseEntity.ok("Login erfolgreich");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Ungültige Anmeldedaten");
        }
    }

    @DeleteMapping("delete/{id}")
    public void delete(@PathVariable String id){
        userService.deleteById(id);
    }

    public record Credentials(String username, String password) {
    }
}
