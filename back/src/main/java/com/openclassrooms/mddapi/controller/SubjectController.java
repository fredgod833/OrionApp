package com.openclassrooms.mddapi.controller;

import com.openclassrooms.mddapi.model.Subject;
import com.openclassrooms.mddapi.model.dto.SubjectDto;
import com.openclassrooms.mddapi.service.SubjectService;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;


import java.util.List;

/**
 * Subject api controller
 */
@RestController
@RequestMapping(path = "api/subject")
public class SubjectController {
    private final SubjectService subjectService;
    public SubjectController(SubjectService subjectService){
        this.subjectService = subjectService;
    }

    /**
     * List of subject
     * @return list
     */
    @GetMapping(path = "/subject_list")
    public List<Subject> subjectList(){
      return subjectService.getSubjectList();
    }

    /**
     * Get subject by its id
     * @param id_subject entry
     * @return subject
     */
    @GetMapping("/{id_subject}")
    public Subject getSubjectById(@PathVariable(name = "id_subject") int id_subject){
        return subjectService.getSubjectById(id_subject);
    }

    /**
     * Get list of subjectDto
     * @return list
     */
    @GetMapping("/subjectDto_list")
    public List<SubjectDto> getSubjectDtoList(){
        return subjectService.findSubjectDtoList();
    }

    /**
     * Create a subject
     * @param subject body entry
     * @return new subject
     */
    @PostMapping("/create_subject")
    public Subject createSubject(@RequestBody Subject subject){
        return subjectService.createSubject(subject);
    }

}
