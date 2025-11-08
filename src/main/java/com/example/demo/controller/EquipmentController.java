package com.example.demo.controller;

import com.example.demo.entities.EquipmentEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/equipment/")
public class EquipmentController {

    @GetMapping("")
    public List<EquipmentEntity> getAll() {
        return null;
    }

    @PostMapping("create")
    public EquipmentEntity create(@RequestBody EquipmentEntity equipmentEntity) {
        return null;
    }
}
