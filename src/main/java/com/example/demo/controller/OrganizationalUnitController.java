package com.example.demo.controller;

import com.example.demo.entities.OrganizationalUnitEntity;
import com.example.demo.service.organizationalUnit.OrganizationalUnitRequest;
import com.example.demo.service.organizationalUnit.OrganizationalUnitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/organizational-unit/")
public class OrganizationalUnitController {

    @Autowired
    private OrganizationalUnitService organizationalUnitService;

    @GetMapping("getAll")
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<OrganizationalUnitEntity> getAll(){
        return organizationalUnitService.getAll();
    }

    @GetMapping("get/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public OrganizationalUnitEntity getById(@PathVariable String id){
        return organizationalUnitService.getById(id);
    }

    @PostMapping("save")
    @PreAuthorize("hasAuthority('ADMIN')")
    public OrganizationalUnitEntity save(@RequestBody OrganizationalUnitRequest request){
        return organizationalUnitService.create(request);
    }

    @DeleteMapping("delete/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public void delete(@PathVariable String id){
        organizationalUnitService.deleteById(id);
    }
}
