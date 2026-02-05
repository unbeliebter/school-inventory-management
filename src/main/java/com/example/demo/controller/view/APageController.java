package com.example.demo.controller.view;

import com.example.demo.entities.IHasId;
import com.example.demo.service.IPageService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

// TODO Finish this abstraction after talking with coworker
@Controller
public abstract class APageController <T extends IHasId> {

    class DTO {
        public List<T> list;

        public List<T> getList() {
            return list;
        }

        public void setList(List<T> list) {
            this.list = list;
        }
    }

    protected String PATH;
    protected IPageService<T> mainService;
    protected List<T> currentTableList;


    @RequestMapping({""})
    @PreAuthorize("hasAuthority('ADMIN')")
    public String showTable(Model model, T newTableItem) {
        List<T> mainEntities = mainService.getAll();

        model.addAttribute("Path", PATH);
        model.addAttribute("TableItems", mainEntities);

        DTO dto = new DTO();
        dto.list = currentTableList;
        model.addAttribute("DTO", dto);

        addAdditionalServicesOrEntitiesToModel(model);

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
        try {
            mainService.deleteById(tableItemId);
        } catch (RuntimeException e) {
            System.out.println("Wow It's catchable!");

        }

        return "redirect:/" + PATH;
    }

    @GetMapping("/export")
    public void exportTableToCsv(HttpServletResponse response) throws IOException {
        String fileName = PATH + "-table.csv";
        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=%s".formatted(fileName));

        mainService.writeToCsv(this.currentTableList, response.getWriter());
    }

    /**
     * Convenient method to insert more stuff
     * @param model
     */
    protected void addAdditionalServicesOrEntitiesToModel(Model model) {
        return;
    }

}
