package com.openclassrooms.mddapi.service;
import com.openclassrooms.mddapi.model.Subject;
import com.openclassrooms.mddapi.model.dto.SubjectDto;
import com.openclassrooms.mddapi.repository.SubjectRepository;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

// TODO: 29/09/2023 Documenter les methodes
@Service
public class SubjectServiceImpl implements SubjectService {

    private final SubjectRepository subjectRepository;
    public SubjectServiceImpl(SubjectRepository subjectRepository){
        this.subjectRepository = subjectRepository;
    }

    public List<SubjectDto> subscribedList(){
        List<Subject> subjectList = new ArrayList<>();
        List<SubjectDto> subjectDtoList = new ArrayList<>();
        try {
            subjectList = getSubjectList();

            for (Subject subject: subjectList) {
                    SubjectDto subjectDto = toDto.apply(subject);

                    subjectDtoList.add(subjectDto);

                    return subjectDtoList;

            }
        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
        return subjectDtoList;
    }

    @Override
    public Subject createSubject(Subject subject) {

        if (subject == null){
            return null;
        }

        try {
           return subjectRepository.save(subject);
        }catch (DataAccessException e){
            throw new RuntimeException("Error while creating subject", e.getCause());
        }
    }

    public List<SubjectDto> findSubjectDtoList(){
        List<Subject> subjectList = new ArrayList<>();
        List<SubjectDto> subjectDtoList = new ArrayList<>();

        try {
            subjectList = subjectRepository.findAll();

            if (subjectList.isEmpty()){
                throw new RuntimeException("Subject list may be empty");
            }

            for (Subject subject: subjectList) {
                SubjectDto subjectDto = SubjectDto.builder()
                        .idSubject(subject.getId_subject())
                        .title(subject.getTitle())
                        .description(subject.getDescription())
                        .build();

                subjectDtoList.add(subjectDto);
            }
        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
        return subjectDtoList;
    }

    public List<Subject> getSubjectList(){

        List<Subject> subjectList  = new ArrayList<>();

        try {
            subjectList = subjectRepository.findAll();

            if (subjectList.isEmpty()){
                throw new RuntimeException("List of subject may be Empty!!!");
            }

        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }

        return subjectList;
    }

    public Subject getSubjectById(int id_subject){
        Subject subject = new Subject();
        try {
            subject = subjectRepository.findById(id_subject).orElse(null);

            if (subject== null){
                return null;
            }
        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
        return subject;
    }
    private final Function<Subject, SubjectDto> toDto = (subject) ->
            SubjectDto.builder()
                    .idSubject(subject.getId_subject())
                    .title(subject.getTitle())
                    .description(subject.getDescription())
                    .build();

}
