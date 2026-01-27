package com.example.demo.controller.view;

import com.example.demo.entities.OrganizationalGroupEntity;
import com.example.demo.entities.OrganizationalUnitEntity;
import com.example.demo.entities.PositionEntity;
import com.example.demo.entities.SubjectEntity;
import com.example.demo.entities.equipment.EquipmentEntity;
import com.example.demo.entities.equipment.EquipmentState;
import com.example.demo.entities.user.UserEntity;
import com.example.demo.service.equipment.EquipmentService;
import com.example.demo.service.organizationalGroup.OrganizationalGroupService;
import com.example.demo.service.organizationalUnit.OrganizationalUnitService;
import com.example.demo.service.position.PositionService;
import com.example.demo.service.subject.SubjectService;
import com.example.demo.service.user.UserService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping({"/"})
public class IndexController {
    final String PATH = "";
    List<EquipmentEntity> currentTableList;

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

    @RequestMapping({""})
    public String showIndex(Model model) {
        model.addAttribute("Path", PATH);

        List<EquipmentEntity> equipment = equipmentService.getAll();
        this.currentTableList = equipment;

        List<SubjectEntity> subjects = subjectService.getAll();
        List<PositionEntity> positions = positionService.getAll();
        List<OrganizationalUnitEntity> units = orgUnitService.getAll();
        List<OrganizationalGroupEntity> groups = orgGroupService.getAll();
        List<UserEntity> users = userService.getAll();
        List<EquipmentState> states = Arrays.asList(EquipmentState.values());

        model.addAttribute("Path", "");

        model.addAttribute("TableItems", equipment);
        model.addAttribute("Subjects", subjects);
        model.addAttribute("Positions", positions);
        model.addAttribute("Units", units);
        model.addAttribute("Groups", groups);
        model.addAttribute("Users", users);
        model.addAttribute("States", states);

        EquipmentEntity newEquipment = new EquipmentEntity();
        model.addAttribute("newTableItem", newEquipment);
        model.addAttribute("newTableItemId", newEquipment.getId());

        return "index";
    }

    @PostMapping("/add")
    public String submitForm(@RequestParam("tableItemId") String equipmentId, @ModelAttribute EquipmentEntity newEquipment) {
        newEquipment.setId(equipmentId.equals("new") ? null : equipmentId);
        equipmentService.save(newEquipment);
        return "redirect:/";
    }

    @PostMapping("/remove")
    public String removeEquipmentEntry(@RequestParam("tableItemId") String equipmentEntryId) {
        equipmentService.deleteById(equipmentEntryId);
        return "redirect:/";
    }

    @GetMapping("/export")
    public void exportEquipmentToCsv(HttpServletResponse response) throws IOException {
        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=table.csv");

        equipmentService.writeEquipmentToCsv(this.currentTableList, response.getWriter());
    }

    @GetMapping("/logout")
    public String logout() {
        return "redirect:/login";
    }
}
