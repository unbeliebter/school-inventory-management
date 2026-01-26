package com.example.demo.controller.view;

import com.example.demo.entities.PositionEntity;
import com.example.demo.entities.user.UserEntity;
import com.example.demo.service.position.PositionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping({"/positions"})
public class PositionsPageController {
    PositionService mainService;
    String PATH = "positions";

    public PositionsPageController(PositionService mainService) {
        this.mainService = mainService;
    }

    @RequestMapping({""})
    public String showIndex(Model model) {
        List<PositionEntity> mainEntities = mainService.getAll();

        model.addAttribute("Path", PATH);
        model.addAttribute("TableItems", mainEntities);

        PositionEntity newTableItem = new PositionEntity();
        model.addAttribute("newTableItem", newTableItem);
        model.addAttribute("newTableItemId", newTableItem.getId());

        return PATH;
    }

    @PostMapping("/add")
    public String submitForm(@RequestParam("tableItemId") String tableItemId, @ModelAttribute PositionEntity newTableItem, Model model) {
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
