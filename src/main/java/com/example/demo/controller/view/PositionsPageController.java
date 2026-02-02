package com.example.demo.controller.view;

import com.example.demo.entities.PositionEntity;
import com.example.demo.service.position.PositionService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping({"/positions"})
public class PositionsPageController extends APageController<PositionEntity> {

    public PositionsPageController(PositionService mainService) {
        this.mainService = mainService;
        PATH = "positions";
    }
}
