package com.openclassrooms.mddapi.service;

import com.openclassrooms.mddapi.model.Subject;
import com.openclassrooms.mddapi.model.dto.SubjectDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SubjectService {
    List<Subject> getSubjectList();
    Subject getSubjectById(int id_subject);
    List<SubjectDto> findSubjectDtoList();
    List<SubjectDto> subscribedList();
    Subject createSubject(Subject subject);
}
