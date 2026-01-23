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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping({"/subjects"})
public class SubjectsController {
    SubjectService subjectService;

    public SubjectsController(SubjectService subjectService) {
        this.subjectService = subjectService;
    }

    @RequestMapping({""})
    public String showIndex(Model model) {
        List<SubjectEntity> subjects = subjectService.getAll();

        model.addAttribute("Subjects", subjects);

        SubjectEntity newSubject = new SubjectEntity();
        model.addAttribute("newSubject", newSubject);
        model.addAttribute("newSubject", newSubject.getId());

        return "subjects";
    }

    @PostMapping("/add")
    public String submitForm(@RequestParam("subjectId") String subjectId, @ModelAttribute SubjectEntity newSubject, Model model) {
        newSubject.setId(subjectId.equals("new") ? null : subjectId);
        subjectService.create(newSubject);
        showIndex(model);
        return "redirect:/subjects";
    }

    @PostMapping("/remove")
    public String removeEquipmentEntry(@RequestParam("subjectEntryId") String subjectEntryId, Model model) {
        subjectService.deleteById(subjectEntryId);
        showIndex(model);
        return "redirect:/subjects";
    }

    @GetMapping("/logout")
    public String logout() {
        return "redirect:/login";
    }
}
