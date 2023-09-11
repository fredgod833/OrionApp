package com.openclassrooms.mddapi.service;

import com.openclassrooms.mddapi.model.Subject;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public interface SubjectService {
    List<Subject> getSubjectList();
}
