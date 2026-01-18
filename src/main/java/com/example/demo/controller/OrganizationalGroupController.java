package com.example.demo.controller;

import com.example.demo.entities.OrganizationalGroupEntity;
import com.example.demo.service.organizationalGroup.OrganizationalGroupRequest;
import com.example.demo.service.organizationalGroup.OrganizationalGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("organizational-group/")
public class OrganizationalGroupController {

    @Autowired
    private OrganizationalGroupService organizationalGroupService;

    @GetMapping("getAll")
    @PreAuthorize("hasRole('ADMIN')")
    public List<OrganizationalGroupEntity> getAll(){
        return organizationalGroupService.getAll();
    }

    @GetMapping("get/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public OrganizationalGroupEntity getById(@PathVariable String id){
        return organizationalGroupService.getById(id);
    }

    @PostMapping("save")
    @PreAuthorize("hasRole('ADMIN')")
    public OrganizationalGroupEntity save(@RequestBody OrganizationalGroupRequest request){
        return organizationalGroupService.create(request);
    }

    @DeleteMapping("delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void delete(@PathVariable String id){
        organizationalGroupService.deleteById(id);
    }
}
