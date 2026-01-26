package com.example.demo.controller.view;

import com.example.demo.entities.OrganizationalGroupEntity;
import com.example.demo.entities.OrganizationalUnitEntity;
import com.example.demo.service.organizationalGroup.OrganizationalGroupService;
import com.example.demo.service.organizationalUnit.OrganizationalUnitService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping({"/units"})
public class OrganizationalUnitsPageController {
    OrganizationalUnitService mainService;
    final String PATH = "units";

    public OrganizationalUnitsPageController(OrganizationalUnitService mainService) {
        this.mainService = mainService;
    }

    @RequestMapping({""})
    public String showIndex(Model model) {
        List<OrganizationalUnitEntity> mainEntities = mainService.getAll();

        model.addAttribute("Path", PATH);
        model.addAttribute("TableItems", mainEntities);

        OrganizationalGroupEntity newTableItem = new OrganizationalGroupEntity();
        model.addAttribute("newTableItem", newTableItem);
        model.addAttribute("newTableItemId", newTableItem.getId());

        return PATH;
    }

    @PostMapping("/add")
    public String submitForm(@RequestParam("tableItemId") String tableItemId, @ModelAttribute OrganizationalUnitEntity newTableItem, Model model) {
        newTableItem.setId(tableItemId.equals("new") ? null : tableItemId);
        mainService.create(newTableItem);
        showIndex(model);
        return "redirect:/" + PATH;
    }

    @PostMapping("/remove")
    public String removeEquipmentEntry(@RequestParam("tableItemId") String tableItemId, Model model) {
        mainService.deleteById(tableItemId);
        showIndex(model);
        return "redirect:/" + PATH;
    }

    @GetMapping("/logout")
    public String logout() {
        return "redirect:/login";
    }
}
