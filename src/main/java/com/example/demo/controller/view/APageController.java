package com.example.demo.controller.view;

import com.example.demo.entities.IHasId;
import com.example.demo.entities.SubjectEntity;
import com.example.demo.service.IPageService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

// TODO Finish this abstraction after talking with coworker
@Controller
public abstract class APageController <T extends IHasId> {
    protected String PATH;
    protected IPageService<T> mainService;
    protected List<T> currentTableList;


    @RequestMapping({""})
    public String showTable(Model model) {
        List<T> mainEntities = mainService.getAll();

        model.addAttribute("Path", PATH);
        model.addAttribute("TableItems", mainEntities);

        SubjectEntity newTableItem = new SubjectEntity();
        model.addAttribute("newTableItem", newTableItem);
        model.addAttribute("newTableItemId", newTableItem.getId());

        return PATH;
    }

    @PostMapping("/add")
    public String submitForm(@RequestParam("tableItemId") String tableItemId, @ModelAttribute T newTableItem, Model model) {
        newTableItem.setId(tableItemId.equals("new") ? null : tableItemId);
        mainService.create(newTableItem);
        return "redirect:/" + PATH;
    }

    @PostMapping("/remove")
    public String removeEntry(@RequestParam("tableItemId") String tableItemId, Model model) {
        mainService.deleteById(tableItemId);
        return "redirect:/" + PATH;
    }

    @GetMapping("/export")
    public void exportTableToCsv(HttpServletResponse response) throws IOException {
        String fileName = PATH + "-table.csv";
        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=%s".formatted(fileName));

        mainService.writeToCsv(this.currentTableList, response.getWriter());
    }

}
