package com.openclassrooms.mddapi.controller;

import com.openclassrooms.mddapi.model.Subject;
import com.openclassrooms.mddapi.repository.SubjectRepository;
import com.openclassrooms.mddapi.service.SubjectService;
import com.openclassrooms.mddapi.service.SubjectServiceImpl;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/api/subject")
public class SubjectController {
    private SubjectService subjectService;

    public SubjectController(SubjectService subjectService){
        this.subjectService = subjectService;
    }

    @GetMapping("/list")
    public List<Subject> subjectList(){
      return subjectService.getSubjectList();
    }

}
