package com.mddcore.usecases.subject;

import com.mddcore.domain.models.Subject;
import com.mddcore.domain.repository.ISubjectRepository;
import com.mddcore.usecases.UseCase;

import java.util.List;

/**
 * Use case for retrieving all subjects from the repository.
 */
public class GetAllSubjectUseCase extends UseCase<GetAllSubjectUseCase.InputValues, GetAllSubjectUseCase.OutputValues> {
    private final ISubjectRepository subjectRepository;

    public GetAllSubjectUseCase(ISubjectRepository subjectRepository) {
        this.subjectRepository = subjectRepository;
    }

    /**
     * Fetches all subjects, returning a list of subjects.
     * @param inputValues the (empty) input values for this use case
     * @return the output values containing a list of all subjects
     */
    @Override
    public OutputValues execute(InputValues inputValues) {
        return new OutputValues(subjectRepository.findAll());
    }

    public record InputValues() implements UseCase.InputValues {}
    public record OutputValues(List<Subject> subjectList) implements UseCase.OutputValues {}
}
