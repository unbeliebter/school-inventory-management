package com.example.demo.controller.view;

import com.example.demo.entities.IHasId;
import com.example.demo.service.IPageService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
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
    public String submitForm(@RequestParam("tableItemId") String tableItemId, @ModelAttribute T newTableItem) {
        newTableItem.setId(tableItemId.equals("new") ? null : tableItemId);
        mainService.create(newTableItem);
        return "redirect:/" + PATH;
    }

    @DeleteMapping("/remove")
    @ResponseBody
    public ResponseEntity<String> removeEntry(@RequestParam("tableItemId") String tableItemId) {
        try {
            mainService.deleteById(tableItemId);
            return ResponseEntity.status(200).body("Entry with id " + tableItemId + " has been deleted");
        } catch (RuntimeException e) {
            return ResponseEntity.status(423).body("The entry you are trying to delete is still in use");
        }
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
