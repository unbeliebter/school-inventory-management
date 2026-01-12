package com.example.demo.controller;

import com.example.demo.entities.equipment.EquipmentEntity;
import com.example.demo.entities.equipment.EquipmentState;
import com.example.demo.service.equipment.EquipmentRequest;
import com.example.demo.service.equipment.EquipmentService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("equipment/")
public class EquipmentController {

    @Autowired
    private EquipmentService equipmentService;

    // api/equipment/getFiltered?state=DELIVERED&group=EDV
    @GetMapping("getFiltered")
    public Page<EquipmentEntity> getFilteredEquipment(
            @RequestParam(required = false) EquipmentState state,
            @RequestParam(required = false) String unit,
            @RequestParam(required = false) String group,
            @RequestParam(required = false) String subject,
            @RequestParam(required = false) String responisbleUser,
            Pageable pageable) {

        return equipmentService.getFilteredEquipment(
                Optional.ofNullable(state),
                Optional.ofNullable(unit),
                Optional.ofNullable(group),
                Optional.ofNullable(subject),
                Optional.ofNullable(responisbleUser),
                pageable
        );
    }

    @GetMapping("getAll")
    public List<EquipmentEntity> getAll() {
        return equipmentService.getAll();
    }

    @GetMapping("get/{id}")
    public EquipmentEntity getById(@PathVariable String id){
        return equipmentService.getById(id);
    }

    @PostMapping("save")
    public EquipmentEntity save(@RequestBody EquipmentRequest request){
        return equipmentService.create(request);
    }

    @DeleteMapping("delete/{id}")
    public void delete(@PathVariable String id){
        equipmentService.deleteById(id);
    }

    @GetMapping("export")
    public void exportEquipmentToCsv(List<EquipmentEntity> toExport, HttpServletResponse response) throws IOException {
        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=equipment.csv");

        equipmentService.writeEquipmentToCsv(toExport, response.getWriter());
    }
}
