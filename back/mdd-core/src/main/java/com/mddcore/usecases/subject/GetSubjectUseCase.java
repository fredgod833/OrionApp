package com.mddcore.usecases.subject;

import com.mddcore.domain.models.Subject;
import com.mddcore.domain.repository.ISubjectRepository;
import com.mddcore.usecases.UseCase;

/**
 * Use case for retrieving a single subject by its ID.
 */

public class GetSubjectUseCase extends UseCase<GetSubjectUseCase.InputValues, GetSubjectUseCase.OutputValues> {
    private final ISubjectRepository subjectRepository;

    public GetSubjectUseCase(ISubjectRepository subjectRepository) {
        this.subjectRepository = subjectRepository;
    }

    /**
     * Fetches a subject by its ID, or throws an exception if not found.
     * @param input the input values containing the ID of the subject to retrieve
     * @return the output values containing the retrieved subject
     * @throws IllegalArgumentException if a subject with the given ID cannot be found
     */
    @Override
    public OutputValues execute(InputValues input) {
        Subject subject = subjectRepository.findById(input.id())
                .orElseThrow(() -> new IllegalArgumentException("Subject not found with id : " + input.id()));

        return new OutputValues(subject);
    }

    public record InputValues(Long id) implements UseCase.InputValues {}

    public record OutputValues(Subject subject) implements UseCase.OutputValues {}
}
