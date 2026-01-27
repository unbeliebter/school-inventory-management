package com.example.demo.controller.view;

import com.example.demo.entities.equipment.EquipmentEntity;
import com.example.demo.service.position.PositionService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;
import java.util.List;

// TODO Finish this abstraction after talking with coworker
//public abstract class APageController <T> {
//    final String PATH = "";
//    PositionService mainService;
//    List<EquipmentEntity> currentTableList;
//
//    @GetMapping("/export")
//    public void exportTableToCsv(HttpServletResponse response) throws IOException {
//        String fileName = PATH + "-table.csv";
//        response.setContentType("text/csv");
//        response.setHeader("Content-Disposition", "attachment; filename=%s".formatted(fileName));
//
//        mainService.writeEquipmentToCsv(this.currentTableList, response.getWriter());
//    }
//
//}
