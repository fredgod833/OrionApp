package com.openclassrooms.mddapi.controller;

import com.openclassrooms.mddapi.model.Subject;
import com.openclassrooms.mddapi.model.dto.SubjectDto;
import com.openclassrooms.mddapi.service.SubjectService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//Subject api controller
@RestController
@RequestMapping(path = "api/subject")
public class SubjectController {
    private final SubjectService subjectService;
    public SubjectController(SubjectService subjectService){
        this.subjectService = subjectService;
    }

    //Return list of subject
    @GetMapping(path = "/subject_list")
    public List<Subject> subjectList(){
      return subjectService.getSubjectList();
    }

    //Return subject by its id
    @GetMapping("/{id_subject}")
    public Subject getSubjectById(@PathVariable(name = "id_subject") int id_subject){
        return subjectService.getSubjectById(id_subject);
    }

    //Return list of subjectDto
    @GetMapping("/subjectDto_list")
    public List<SubjectDto> getSubjectDtoList(){
        return subjectService.findSubjectDtoList();
    }

    //Create a subject
    @PostMapping("/create_subject")
    public Subject createSubject(@RequestBody Subject subject){
        return subjectService.createSubject(subject);
    }

}
