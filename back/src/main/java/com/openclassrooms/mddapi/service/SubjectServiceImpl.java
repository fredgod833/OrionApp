package com.openclassrooms.mddapi.service;
import com.openclassrooms.mddapi.model.Subject;
import com.openclassrooms.mddapi.model.dto.SubjectDto;
import com.openclassrooms.mddapi.repository.SubjectRepository;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

//Layer interface implementation
@Service
public class SubjectServiceImpl implements SubjectService {

    private final SubjectRepository subjectRepository;
    public SubjectServiceImpl(SubjectRepository subjectRepository){
        this.subjectRepository = subjectRepository;
    }

    //TODO: Unused method
    //Load list of subscribed subjects
    public List<SubjectDto> subscribedList(){
        //Initialise lists
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

    //Persist a new subject
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

    //Load list of subjectsDto
    public List<SubjectDto> findSubjectDtoList(){
        //List initialization
        List<Subject> subjectList = new ArrayList<>();
        List<SubjectDto> subjectDtoList = new ArrayList<>();

        try {
            //Stock list of subject
            subjectList = subjectRepository.findAll();

            if (subjectList.isEmpty()){
                throw new RuntimeException("Subject list may be empty");
            }

            //Set subject to dto
            for (Subject subject: subjectList) {
                SubjectDto subjectDto = SubjectDto.builder()
                        .idSubject(subject.getIdSubject())
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
    //Load list of subject
    public List<Subject> getSubjectList(){
        //Initialise list of subjects
        List<Subject> subjectList  = new ArrayList<>();

        try {
            //Stock list of subject
            subjectList = subjectRepository.findAll();

            if (subjectList.isEmpty()){
                throw new RuntimeException("List of subject may be Empty!!!");
            }

        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }

        return subjectList;
    }
    //Load subject by its id
    public Subject getSubjectById(int id_subject){
        //Initialise subject
        Subject subject = new Subject();
        try {
            //Stock subject
            subject = subjectRepository.findById(id_subject).orElse(null);

            if (subject== null){
                return null;
            }
        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
        return subject;
    }
    //TODO: Delete unused method
   private final Function<Subject, SubjectDto> toDto = (subject) ->
            SubjectDto.builder()
                    .idSubject(subject.getIdSubject())
                    .title(subject.getTitle())
                    .description(subject.getDescription())
                    .build();

}
