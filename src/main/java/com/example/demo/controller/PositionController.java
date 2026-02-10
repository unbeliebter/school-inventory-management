package com.example.demo.controller;

import com.example.demo.entities.PositionEntity;
import com.example.demo.service.position.PositionRequest;
import com.example.demo.service.position.PositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/position/")
public class PositionController {

    @Autowired
    private PositionService positionService;

    @GetMapping("getAll")
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<PositionEntity> getAll(){
        return positionService.getAll();
    }

    @GetMapping("get/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public PositionEntity getById(@PathVariable String id){
        return positionService.getById(id);
    }

    @PostMapping("save")
    @PreAuthorize("hasAuthority('ADMIN')")
    public PositionEntity save(@RequestBody PositionRequest request){
        return positionService.create(request);
    }

    @DeleteMapping("delete/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public void delete(@PathVariable String id){
        positionService.deleteById(id);
    }
}
