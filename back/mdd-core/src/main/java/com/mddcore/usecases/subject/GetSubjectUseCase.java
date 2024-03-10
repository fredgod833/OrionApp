package com.mddcore.usecases.subject;

import com.mddcore.domain.models.Subject;
import com.mddcore.domain.repository.ISubjectRepository;
import com.mddcore.usecases.UseCase;

public class GetSubjectUseCase extends UseCase<GetSubjectUseCase.InputValues, GetSubjectUseCase.OutputValues> {
    private final ISubjectRepository subjectRepository;

    public GetSubjectUseCase(ISubjectRepository subjectRepository) {
        this.subjectRepository = subjectRepository;
    }

    @Override
    public OutputValues execute(InputValues input) {
        Subject subject = subjectRepository.findById(input.id()).orElse(null);

        if (subject == null) {
            throw new IllegalArgumentException("Subject not found with id : " + input.id());
        }

        return new OutputValues(subject);
    }

    public record InputValues(Long id) implements UseCase.InputValues {}

    public record OutputValues(Subject subject) implements UseCase.OutputValues {}
}
