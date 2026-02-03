package com.example.demo.controller;

import com.example.demo.entities.UserPasswordChangeEntity;
import com.example.demo.service.user.services.UserPasswordChangeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("users/password-changes")
public class UserPasswordChangeController {

    @Autowired
    private UserPasswordChangeService passwordChangeService;

    @GetMapping("getAll")
    @PreAuthorize("hasRole('ADMIN')")
    public List<UserPasswordChangeEntity> getAll(){
        return passwordChangeService.getAll();
    }

    @GetMapping("get/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public UserPasswordChangeEntity getById(@PathVariable String id){
        return passwordChangeService.getById(id);
    }

    @GetMapping("get-for-user/{user-id}")
    @PreAuthorize("hasRole('ADMIN')")
    public List<UserPasswordChangeEntity> getByUserId(@PathVariable("user-id") String id){
        return passwordChangeService.getByUserId(id);
    }
}
