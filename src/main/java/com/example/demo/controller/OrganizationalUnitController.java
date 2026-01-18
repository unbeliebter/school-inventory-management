package com.example.demo.controller;

import com.example.demo.entities.OrganizationalUnitEntity;
import com.example.demo.service.organizationalUnit.OrganizationalUnitRequest;
import com.example.demo.service.organizationalUnit.OrganizationalUnitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("organizational-unit/")
public class OrganizationalUnitController {

    @Autowired
    private OrganizationalUnitService organizationalUnitService;

    @GetMapping("getAll")
    @PreAuthorize("hasRole('ADMIN')")
    public List<OrganizationalUnitEntity> getAll(){
        return organizationalUnitService.getAll();
    }

    @GetMapping("get/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public OrganizationalUnitEntity getById(@PathVariable String id){
        return organizationalUnitService.getById(id);
    }

    @PostMapping("save")
    @PreAuthorize("hasRole('ADMIN')")
    public OrganizationalUnitEntity save(@RequestBody OrganizationalUnitRequest request){
        return organizationalUnitService.create(request);
    }

    @DeleteMapping("delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void delete(@PathVariable String id){
        organizationalUnitService.deleteById(id);
    }
}
