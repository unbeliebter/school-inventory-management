package com.example.demo.controller;

import com.example.demo.entities.equipment.EquipmentEntity;
import com.example.demo.service.EquipmentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/equipment/")
public class EquipmentController {

    private EquipmentService equipmentService;

    @GetMapping("getAll")
    public List<EquipmentEntity> getAll(){
        return equipmentService.getAll();
    }

    @GetMapping("get/{id}")
    public EquipmentEntity getById(@PathVariable String id){
        return equipmentService.getById(id);
    }

    @PostMapping("save")
    public EquipmentEntity save(@RequestBody EquipmentEntity entity){
        return equipmentService.createOrUpdate(entity);
    }

    @DeleteMapping("delete/{id}")
    public void delete(@PathVariable String id){
        equipmentService.deleteById(id);
    }
}
