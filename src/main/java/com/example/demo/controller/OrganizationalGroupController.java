package com.example.demo.controller;

import com.example.demo.entities.OrganizationalGroupEntity;
import com.example.demo.service.OrganizationalGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("organizational-group/")
public class OrganizationalGroupController {

    @Autowired
    private OrganizationalGroupService organizationalGroupService;

    @GetMapping("getAll")
    public List<OrganizationalGroupEntity> getAll(){
        return organizationalGroupService.getAll();
    }

    @GetMapping("get/{id}")
    public OrganizationalGroupEntity getById(@PathVariable String id){
        return organizationalGroupService.getById(id);
    }

    @PostMapping("save")
    public OrganizationalGroupEntity save(@RequestBody OrganizationalGroupEntity entity){
        return organizationalGroupService.createOrUpdate(entity);
    }

    @DeleteMapping("delete/{id}")
    public void delete(@PathVariable String id){
        organizationalGroupService.deleteById(id);
    }
}
