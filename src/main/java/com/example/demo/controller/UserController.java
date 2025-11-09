package com.example.demo.controller;

import com.example.demo.entities.user.UserEntity;
import com.example.demo.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public UserEntity getById(@PathVariable String id){
        return userService.getById(id);
    }

    @PostMapping("save")
    public UserEntity save(@RequestBody UserEntity entity){
        return userService.createOrUpdate(entity);
    }

    @DeleteMapping("delete/{id}")
    public void delete(@PathVariable String id){
        userService.deleteById(id);
    }
}
