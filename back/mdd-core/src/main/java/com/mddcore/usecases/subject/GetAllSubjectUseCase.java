package com.mddcore.usecases.subject;

import com.mddcore.domain.models.Subject;
import com.mddcore.domain.repository.ISubjectRepository;
import com.mddcore.usecases.UseCase;

import java.util.List;

public class GetAllSubjectUseCase extends UseCase<GetAllSubjectUseCase.InputValues, GetAllSubjectUseCase.OutputValues> {
    private final ISubjectRepository subjectRepository;

    public GetAllSubjectUseCase(ISubjectRepository subjectRepository) {
        this.subjectRepository = subjectRepository;
    }

    @Override
    public OutputValues execute(InputValues inputValues) {
        return new OutputValues(subjectRepository.findAll());
    }

    public record InputValues() implements UseCase.InputValues {}
    public record OutputValues(List<Subject> subjectList) implements UseCase.OutputValues {}
}
