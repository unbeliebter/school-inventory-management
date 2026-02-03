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
import com.example.demo.service.user.services.UserService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping({"/inventory"})
public class InventoryPageController {
    final String PATH = "inventory";
    boolean filtered = false;
    List<EquipmentEntity> currentTableList;
    EquipmentService mainService;

    SubjectService subjectService;
    PositionService positionService;
    OrganizationalUnitService orgUnitService;
    OrganizationalGroupService orgGroupService;
    UserService userService;


    public InventoryPageController(EquipmentService equipmentService, SubjectService subjectService,
                                   PositionService positionService, OrganizationalUnitService orgUnitService,
                                   OrganizationalGroupService orgGroupService, UserService userService) {
        this.mainService = equipmentService;
        this.subjectService = subjectService;
        this.positionService = positionService;
        this.orgUnitService = orgUnitService;
        this.orgGroupService = orgGroupService;
        this.userService = userService;
    }

    @RequestMapping({""})
    public String showInventory(Model model) {
        if (!filtered) {
            this.currentTableList = mainService.getAll();
        }
        this.filtered = false;
        model.addAttribute("TableItems", this.currentTableList);
        model.addAttribute("Path", PATH);

        addAdditionalServicesToModel(model);

        return PATH;
    }

    @PostMapping("/filtered")
    public String filter(
            @RequestParam(required = false) String state,
            @RequestParam(required = false) String unit,
            @RequestParam(required = false) String group,
            @RequestParam(required = false) String subject,
            @RequestParam(required = false) String responsibleUser,
            @RequestParam(required = false) String position) {

        state = state.isEmpty() ? null : state;
        unit = unit.isEmpty() ? null : unit;
        group = group.isEmpty() ? null : group;
        subject = subject.isEmpty() ? null : subject;
        responsibleUser = responsibleUser.isEmpty() ? null : responsibleUser;
        position = position.isEmpty() ? null : position;

        this.currentTableList = mainService.getFilteredEquipmentAsList(
                Optional.ofNullable(state),
                Optional.ofNullable(unit),
                Optional.ofNullable(group),
                Optional.ofNullable(subject),
                Optional.ofNullable(responsibleUser),
                Optional.ofNullable(position));
        this.filtered = true;



        return "redirect:/" + PATH;
    }

    @PostMapping("/add")
    public String submitForm(@RequestParam("tableItemId") String equipmentId, @ModelAttribute EquipmentEntity newEquipment) {
        newEquipment.setId(equipmentId.equals("new") ? null : equipmentId);
        mainService.save(newEquipment);
        return "redirect:/" + PATH;
    }

    @PostMapping("/remove")
    public String removeEquipmentEntry(@RequestParam("tableItemId") String equipmentEntryId) {
        mainService.deleteById(equipmentEntryId);
        return "redirect:/" + PATH;
    }

    @GetMapping("/export")
    public void exportTableToCsv(HttpServletResponse response) throws IOException {
        String fileName = PATH + "-table.csv";
        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=%s".formatted(fileName));

        mainService.writeToCsv(this.currentTableList, response.getWriter());
    }


    private void addAdditionalServicesToModel(Model model) {
        List<SubjectEntity> subjects = subjectService.getAll();
        List<PositionEntity> positions = positionService.getAll();
        List<OrganizationalUnitEntity> units = orgUnitService.getAll();
        List<OrganizationalGroupEntity> groups = orgGroupService.getAll();
        List<UserEntity> users = userService.getAll();
        List<EquipmentState> states = Arrays.asList(EquipmentState.values());

        model.addAttribute("Subjects", subjects);
        model.addAttribute("Positions", positions);
        model.addAttribute("Units", units);
        model.addAttribute("Groups", groups);
        model.addAttribute("Users", users);
        model.addAttribute("States", states);

        EquipmentEntity newEquipment = new EquipmentEntity();
        model.addAttribute("newTableItem", newEquipment);
        model.addAttribute("newTableItemId", newEquipment.getId());
    }
}
