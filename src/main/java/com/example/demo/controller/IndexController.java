package com.example.demo.controller;

import com.example.demo.entities.OrganizationalGroupEntity;
import com.example.demo.entities.OrganizationalUnitEntity;
import com.example.demo.entities.PositionEntity;
import com.example.demo.entities.SubjectEntity;
import com.example.demo.entities.equipment.EquipmentEntity;
import com.example.demo.entities.equipment.EquipmentState;
import com.example.demo.entities.user.UserEntity;
import com.example.demo.service.equipment.EquipmentRequest;
import com.example.demo.service.equipment.EquipmentService;
import com.example.demo.service.organizationalGroup.OrganizationalGroupService;
import com.example.demo.service.organizationalUnit.OrganizationalUnitService;
import com.example.demo.service.position.PositionService;
import com.example.demo.service.subject.SubjectService;
import com.example.demo.service.user.UserService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@Controller
public class IndexController {

    EquipmentService equipmentService;
    SubjectService subjectService;
    PositionService positionService;
    OrganizationalUnitService orgUnitService;
    OrganizationalGroupService orgGroupService;
    UserService userService;


    public IndexController(EquipmentService equipmentService, SubjectService subjectService,
                           PositionService positionService, OrganizationalUnitService orgUnitService,
                           OrganizationalGroupService orgGroupService, UserService userService) {
        this.equipmentService = equipmentService;
        this.subjectService = subjectService;
        this.positionService = positionService;
        this.orgUnitService = orgUnitService;
        this.orgGroupService = orgGroupService;
        this.userService = userService;
    }

    @RequestMapping({"/"})
    public String showIndex(Model model) {
        List<EquipmentEntity> equipment = equipmentService.getAll();
        List<SubjectEntity> subjects = subjectService.getAll();
        List<PositionEntity> positions = positionService.getAll();
        List<OrganizationalUnitEntity> units = orgUnitService.getAll();
        List<OrganizationalGroupEntity> groups = orgGroupService.getAll();
        List<UserEntity> users = userService.getAll();
        List<EquipmentState> states = Arrays.asList(EquipmentState.values());

        model.addAttribute("Equipment", equipment);
        model.addAttribute("Subjects", subjects);
        model.addAttribute("Positions", positions);
        model.addAttribute("Units", units);
        model.addAttribute("Groups", groups);
        model.addAttribute("Users", users);
        model.addAttribute("States", states);

        EquipmentEntity newEquipment = new EquipmentEntity();
        model.addAttribute("newEquipment", newEquipment);

        return "index";
    }

    @PostMapping("/addEquipment")
    public String submitForm(@ModelAttribute EquipmentEntity newEquipment, Model model) {
        equipmentService.save(newEquipment);
        return showIndex(model);
    }

    @PostMapping("/removeEquipment")
    public String removeEquipmentEntry(@RequestParam("equipmentEntryId") String equipmentEntryId, Model model) {
        equipmentService.deleteById(equipmentEntryId);
        showIndex(model);
        return "redirect:/";
//        return showIndex(model);
    }
}
