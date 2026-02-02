package com.example.demo.controller.view;

import com.example.demo.entities.SubjectEntity;
import com.example.demo.service.subject.SubjectService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping({"/subjects"})
public class SubjectsPageController extends APageController<SubjectEntity> {
//    IPageService<SubjectEntity> mainService;
//    final String PATH = "subjects";

    public SubjectsPageController(SubjectService mainService) {
        this.mainService = mainService;
        PATH = "subjects";
    }

//    @RequestMapping({""})
//    public String showIndex(Model model) {
//        List<SubjectEntity> mainEntities = mainService.getAll();
//
//        model.addAttribute("Path", PATH);
//        model.addAttribute("TableItems", mainEntities);
//
//        SubjectEntity newTableItem = new SubjectEntity();
//        model.addAttribute("newTableItem", newTableItem);
//        model.addAttribute("newTableItemId", newTableItem.getId());
//
//        return PATH;
//    }
//
//    @PostMapping("/add")
//    public String submitForm(@RequestParam("tableItemId") String tableItemId, @ModelAttribute SubjectEntity newTableItem, Model model) {
//        newTableItem.setId(tableItemId.equals("new") ? null : tableItemId);
//        mainService.create(newTableItem);
//        return "redirect:/" + PATH;
//    }
//
//    @PostMapping("/remove")
//    public String removeEquipmentEntry(@RequestParam("tableItemId") String tableItemId, Model model) {
//        mainService.deleteById(tableItemId);
//        return "redirect:/" + PATH;
//    }
}
