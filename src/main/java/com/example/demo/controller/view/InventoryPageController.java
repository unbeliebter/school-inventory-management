package com.example.demo.controller.view;

import com.example.demo.entities.OrganizationalGroupEntity;
import com.example.demo.entities.OrganizationalUnitEntity;
import com.example.demo.entities.PositionEntity;
import com.example.demo.entities.SubjectEntity;
import com.example.demo.entities.equipment.EquipmentEntity;
import com.example.demo.entities.equipment.EquipmentRenterEntity;
import com.example.demo.entities.equipment.EquipmentState;
import com.example.demo.entities.user.UserEntity;
import com.example.demo.service.equipment.EquipmentService;
import com.example.demo.service.equipment.equipmentrenter.EquipmentRenterService;
import com.example.demo.service.organizationalGroup.OrganizationalGroupService;
import com.example.demo.service.organizationalUnit.OrganizationalUnitService;
import com.example.demo.service.position.PositionService;
import com.example.demo.service.subject.SubjectService;
import com.example.demo.service.user.services.UserService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.*;
import java.util.logging.Logger;


@Controller
@RequestMapping({"/inventory"})
public class InventoryPageController {

    public final static Logger LOGGER = Logger.getLogger(InventoryPageController.class.getName());

    public static class DTO {
        @Getter
        @Setter
        public List<EquipmentEntity> list;
    }

    final String PATH = "inventory";
    EquipmentService mainService;

    SubjectService subjectService;
    PositionService positionService;
    OrganizationalUnitService orgUnitService;
    OrganizationalGroupService orgGroupService;
    UserService userService;
    EquipmentRenterService equipmentRenterService;


    public InventoryPageController(EquipmentService equipmentService, SubjectService subjectService,
                                   PositionService positionService, OrganizationalUnitService orgUnitService,
                                   OrganizationalGroupService orgGroupService, UserService userService, EquipmentRenterService equipmentRenterService) {
        this.mainService = equipmentService;
        this.subjectService = subjectService;
        this.positionService = positionService;
        this.orgUnitService = orgUnitService;
        this.orgGroupService = orgGroupService;
        this.userService = userService;
        this.equipmentRenterService = equipmentRenterService;
    }

    @RequestMapping({""})
    public String showInventory(@RequestParam(required = false) Boolean filter,
                                @RequestParam(required = false) String inventoryNumber,
                                @RequestParam(required = false) String itemName,
                                @RequestParam(required = false) String state,
                                @RequestParam(required = false) String unit,
                                @RequestParam(required = false) String group,
                                @RequestParam(required = false) String subject,
                                @RequestParam(required = false) String responsibleUser,
                                @RequestParam(required = false) String position,
                                Model model, Authentication auth) {

        List<EquipmentEntity> currentTableList;
        if (filter != null && filter) {
            currentTableList = filter(inventoryNumber, itemName,state,unit,group,subject,responsibleUser,position);
        } else {
            currentTableList = mainService.getAll();
        }
        model.addAttribute("TableItems", currentTableList);
        model.addAttribute("Path", PATH);

        DTO dto = new DTO();
        dto.list = currentTableList;
        model.addAttribute("DTO", dto);

        String currentUserId = userService.findByUsername(auth.getName()).getId();
        String currentUserRole = userService.getById(currentUserId).getRole().getName();
        model.addAttribute("currentUserId", currentUserId);
        model.addAttribute("currentUserRole", currentUserRole);

        addAdditionalServicesToModel(model);

        return PATH;
    }

    public List<EquipmentEntity> filter(String inventoryNumber,
                                        String itemName,
                                        String state,
                                        String unit,
                                        String group,
                                        String subject,
                                        String responsibleUser,
                                        String position) {

        inventoryNumber = inventoryNumber.isEmpty() ? null : inventoryNumber;
        itemName = itemName.isEmpty() ? null : itemName;
        state = state.isEmpty() ? null : state;
        unit = unit.isEmpty() ? null : unit;
        group = group.isEmpty() ? null : group;
        subject = subject.isEmpty() ? null : subject;
        responsibleUser = responsibleUser.isEmpty() ? null : responsibleUser;
        position = position.isEmpty() ? null : position;


        return mainService.getFilteredEquipmentAsList(
                Optional.ofNullable(inventoryNumber),
                Optional.ofNullable(itemName),
                Optional.ofNullable(state),
                Optional.ofNullable(unit),
                Optional.ofNullable(group),
                Optional.ofNullable(subject),
                Optional.ofNullable(responsibleUser),
                Optional.ofNullable(position));
    }

    @PostMapping("/add")
    public String submitForm(@RequestParam("tableItemId") String equipmentId, @RequestParam(name="renterName", required = false ) String renterName, @ModelAttribute EquipmentEntity newEquipment) {
        if (!equipmentId.equals("new")) {
            newEquipment.setId(equipmentId);
            removeRenterIfEntityWasOnLoan(equipmentId);
        } else {
            newEquipment.setId(null);
        }

        mainService.save(newEquipment);

        if (!renterName.isEmpty()) {
            equipmentRenterService.create(newEquipment, renterName);
        }

        return "redirect:/" + PATH;
    }

    @DeleteMapping("/remove")
    public String removeEquipmentEntry(@RequestParam("tableItemId") String equipmentEntryId) {
        removeRenterIfEntityWasOnLoan(equipmentEntryId);
        mainService.deleteById(equipmentEntryId);
        return "redirect:/" + PATH;
    }

    @RequestMapping("/export")
    public void exportTableToCsv(@ModelAttribute("DTO") DTO dto, HttpServletResponse response) throws IOException {
        String fileName = PATH + "-table.csv";
        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=%s".formatted(fileName));

        mainService.writeToCsv(dto.list, response.getWriter());
    }

    private void removeRenterIfEntityWasOnLoan(String equipmentId) {
        EquipmentEntity oldEntity = mainService.getById(equipmentId);
        if (oldEntity.getEquipmentState() != EquipmentState.ON_LOAN) {
            return;
        }
        try {
            equipmentRenterService.delete(oldEntity);
        } catch (IllegalStateException e) {
            LOGGER.warning("Beim versuch einen Inventargegenstand der sich im Zustand " +
                    "ausgeliehen befindet zu löschen wurde kein passender Renter gefunden");
        }


    }

    private void addAdditionalServicesToModel(Model model) {
        List<SubjectEntity> subjects = subjectService.getAll();
        List<PositionEntity> positions = positionService.getAll();
        List<OrganizationalUnitEntity> units = orgUnitService.getAll();
        List<OrganizationalGroupEntity> groups = orgGroupService.getAll();
        List<UserEntity> users = userService.getAll();
        List<EquipmentState> states = Arrays.asList(EquipmentState.values());

        List<EquipmentEntity> items = mainService.getAll();

        Set<String> itemNameFilterOptions = new HashSet<>();
        for (EquipmentEntity e : items) {
            itemNameFilterOptions.add(e.getEquipmentName());
        }
        model.addAttribute("ItemNameFilterOptions", itemNameFilterOptions);

        Map<String, EquipmentRenterEntity> renterMap = new HashMap<>();
        for (EquipmentRenterEntity e : equipmentRenterService.getAll()) {
            renterMap.put(e.getEquipment().getId(), e);
        }
        model.addAttribute("renterMap", renterMap);




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
