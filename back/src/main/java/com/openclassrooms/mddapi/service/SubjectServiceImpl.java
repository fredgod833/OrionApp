package com.openclassrooms.mddapi.service;

import com.openclassrooms.mddapi.model.Subject;
import com.openclassrooms.mddapi.repository.SubjectRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class SubjectServiceImpl implements SubjectService {

    private final SubjectRepository subjectRepository;

    public List<Subject> getSubjectList(){
        List<Subject> subjectList = new ArrayList<>();
        try{
           subjectList = subjectRepository.findAll();

           if (!subjectList.isEmpty()){
               return subjectList;
           }
        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
        return subjectList;
    }

}
