package com.example.demo.controller;

import com.example.demo.entities.equipment.EquipmentEntity;
import com.example.demo.entities.user.UserEntity;
import com.example.demo.service.equipment.EquipmentService;
import com.example.demo.service.user.UserService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class IndexController {

    EquipmentService equipmentService;

    public IndexController(EquipmentService equipmentService) {
        this.equipmentService = equipmentService;
    }

    @RequestMapping({"/"})
    public String showIndex(Model model) {
        List<EquipmentEntity> equipment = equipmentService.getAll();
        model.addAttribute("Equipment", equipment);
        return "index";
    }
}
