package com.example.demo.controller;

import com.example.demo.entities.OrganizationalUnitEntity;
import com.example.demo.service.OrganizationalUnitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("organizational-unit/")
public class OrganizationalUnitController {

    @Autowired
    private OrganizationalUnitService organizationalUnitService;

    @GetMapping("getAll")
    public List<OrganizationalUnitEntity> getAll(){
        return organizationalUnitService.getAll();
    }

    @GetMapping("get/{id}")
    public OrganizationalUnitEntity getById(@PathVariable String id){
        return organizationalUnitService.getById(id);
    }

    @PostMapping("save")
    public OrganizationalUnitEntity save(@RequestBody OrganizationalUnitEntity entity){
        return organizationalUnitService.createOrUpdate(entity);
    }

    @DeleteMapping("delete/{id}")
    public void delete(@PathVariable String id){
        organizationalUnitService.deleteById(id);
    }
}
