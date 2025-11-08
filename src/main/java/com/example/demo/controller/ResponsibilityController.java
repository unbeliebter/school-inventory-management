package com.example.demo.controller;

import com.example.demo.entities.ResponsibilityEntity;
import com.example.demo.service.ResponisbilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("responsibility/")
public class ResponsibilityController {

    @Autowired
    private ResponisbilityService responisbilityService;

    @GetMapping("getAll")
    public List<ResponsibilityEntity> getAll(){
        return responisbilityService.getAll();
    }

    @GetMapping("get/{id}")
    public ResponsibilityEntity getById(@PathVariable String id){
        return responisbilityService.getById(id);
    }

    @PostMapping("save")
    public ResponsibilityEntity save(@RequestBody ResponsibilityEntity entity){
        return responisbilityService.createOrUpdate(entity);
    }

    @DeleteMapping("delete/{id}")
    public void delete(@PathVariable String id){
        responisbilityService.deleteById(id);
    }
}
