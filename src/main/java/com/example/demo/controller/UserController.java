package com.example.demo.controller;

import com.example.demo.entities.user.UserEntity;
import com.example.demo.service.user.PasswordHandler;
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
@RequestMapping("api/users/")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private AuthenticationManager authManager;
    @Autowired
    private PasswordHandler passwordHandler;

    @GetMapping("getAll")
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<UserEntity> getAll(){
        return userService.getAll();
    }

    @GetMapping("get/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public UserEntity getById(@PathVariable String id){
        return userService.getById(id);
    }

    @PostMapping("create")
    @PreAuthorize("hasAuthority('ADMIN')")
    public UserEntity save(@RequestBody UserRequest request){
        return userService.create(request);
    }

    @PostMapping("login")
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
    @PreAuthorize("hasAuthority('ADMIN')")
    public void delete(@PathVariable String id) {
        userService.deleteById(id);
    }

    @PatchMapping("reset-password/{user-id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String resetPassword(@PathVariable("user-id") String userId) {
        String rawPassword = passwordHandler.generateOneTimePassword();

        var user = userService.getById(userId);
        throwExceptionIfUserAbsent(user);
        setNewPassword(user, rawPassword, false);

        return rawPassword;
    }

    @PatchMapping("request-password-change")
    public void requestPasswordChange(@RequestBody String username) {
        var user = userService.findByUsername(username);
        throwExceptionIfUserAbsent(user);

        user.setRequiresPasswordReset(true);
        userService.save(user);
    }

    private static void throwExceptionIfUserAbsent(UserEntity user) {
        if (user == null) {
            throw new IllegalStateException("Benutzer nicht vorhanden für Passwort-Reset");
        }
    }

    @PatchMapping("change-password")
    public void changePassword(@RequestBody ChangePassword credentials) {
        var user = userService.findByUsername(credentials.username());
        throwExceptionIfUserAbsent(user);

        if (passwordHandler.encodePassword(credentials.passwordBefore(), user.getPassword())) {
            setNewPassword(user, credentials.passwordNew(), true);
        }
    }

    private void setNewPassword(UserEntity user, String credentials, boolean changedPassword) {
        user.setPassword(passwordHandler.hashPassword(credentials));
        user.setChangedPassword(changedPassword);
        user.setRequiresPasswordReset(false);
        userService.save(user);
    }


    public record Credentials(String username, String password) {
    }

    public record ChangePassword(String username, String passwordBefore, String passwordNew) {

    }
}
