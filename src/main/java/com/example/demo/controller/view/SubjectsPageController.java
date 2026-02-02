package com.example.demo.controller.view;

import com.example.demo.entities.SubjectEntity;
import com.example.demo.service.subject.SubjectService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping({"/subjects"})
public class SubjectsPageController extends APageController<SubjectEntity> {
    public SubjectsPageController(SubjectService mainService) {
        this.mainService = mainService;
        PATH = "subjects";
    }
}
