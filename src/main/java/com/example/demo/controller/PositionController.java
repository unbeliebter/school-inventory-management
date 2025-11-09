package com.example.demo.controller;

import com.example.demo.entities.PositionEntity;
import com.example.demo.service.position.PositionService;
import com.example.demo.service.position.PostionRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("position/")
public class PositionController {

    @Autowired
    private PositionService positionService;

    @GetMapping("getAll")
    public List<PositionEntity> getAll(){
        return positionService.getAll();
    }

    @GetMapping("get/{id}")
    public PositionEntity getById(@PathVariable String id){
        return positionService.getById(id);
    }

    @PostMapping("save")
    public PositionEntity save(@RequestBody PostionRequest request){
        return positionService.create(request);
    }

    @DeleteMapping("delete/{id}")
    public void delete(@PathVariable String id){
        positionService.deleteById(id);
    }
}
