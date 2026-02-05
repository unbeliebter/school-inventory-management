package com.example.demo.controller;

import com.example.demo.entities.equipment.EquipmentEntity;
import com.example.demo.service.equipment.EquipmentRequest;
import com.example.demo.service.equipment.EquipmentService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/equipment/")
public class EquipmentController {

    @Autowired
    private EquipmentService equipmentService;

    // api/equipment/getFiltered?state=DELIVERED&group=EDV
    @GetMapping("getFiltered")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'RESPONSIBLE', 'TEACHER', 'SENIOR_RESPONSIBLE')")
    public Page<EquipmentEntity> getFilteredEquipment(
            @RequestParam(required = false) String inventoryNumber,
            @RequestParam(required = false) String itemName,
            @RequestParam(required = false) String state,
            @RequestParam(required = false) String unit,
            @RequestParam(required = false) String group,
            @RequestParam(required = false) String subject,
            @RequestParam(required = false) String responsibleUser,
            @RequestParam(required = false) String position,
            Pageable pageable) {

        return equipmentService.getFilteredEquipment(
                Optional.ofNullable(inventoryNumber),
                Optional.ofNullable(itemName),
                Optional.ofNullable(state),
                Optional.ofNullable(unit),
                Optional.ofNullable(group),
                Optional.ofNullable(subject),
                Optional.ofNullable(responsibleUser),
                Optional.ofNullable(position),
                pageable
        );
    }

    @GetMapping("getAll")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'RESPONSIBLE', 'TEACHER', 'SENIOR_RESPONSIBLE')")
    public List<EquipmentEntity> getAll() {
        return equipmentService.getAll();
    }

    @GetMapping("get/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'RESPONSIBLE', 'TEACHER', 'SENIOR_RESPONSIBLE')")
    public EquipmentEntity getById(@PathVariable String id){
        return equipmentService.getById(id);
    }

    @PostMapping("save")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'RESPONSIBLE', 'SENIOR_RESPONSIBLE')")
    public EquipmentEntity save(@RequestBody EquipmentRequest request){
        return equipmentService.save(request);
    }

    @DeleteMapping("delete/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'RESPONSIBLE', 'SENIOR_RESPONSIBLE')")
    public void delete(@PathVariable String id){
        equipmentService.deleteById(id);
    }

    @GetMapping("export")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'RESPONSIBLE', 'TEACHER', 'SENIOR_RESPONSIBLE')")
    public void exportEquipmentToCsv(List<EquipmentEntity> toExport, HttpServletResponse response) throws IOException {
        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=equipment.csv");

        equipmentService.writeToCsv(toExport, response.getWriter());
    }
}
