package com.openclassrooms.mddapi.controller;

import com.openclassrooms.mddapi.model.Subject;
import com.openclassrooms.mddapi.model.dto.SubjectDto;
import com.openclassrooms.mddapi.service.SubjectService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/subject")
public class SubjectController {
    private final SubjectService subjectService;
    public SubjectController(SubjectService subjectService){
        this.subjectService = subjectService;
    }
    @GetMapping(path = "/subject_list")
    public List<Subject> subjectList(){
      return subjectService.getSubjectList();
    }
    @GetMapping("/{id_subject}")
    public Subject getSubjectById(@PathVariable(name = "id_subject") int id_subject){
        return subjectService.getSubjectById(id_subject);
    }
    @GetMapping("/subjectDto_list")
    public List<SubjectDto> getSubjectDtoList(){
        return subjectService.findSubjectDtoList();
    }

    @PostMapping("/create_subject")
    public String createSubject(@RequestBody Subject subject){
        return subjectService.createSubject(subject);
    }

}
