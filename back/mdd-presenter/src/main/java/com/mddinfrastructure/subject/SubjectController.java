package com.mddinfrastructure.subject;

import com.mddcore.usecases.UseCaseExecutor;
import com.mddcore.usecases.subject.GetAllSubjectUseCase;
import com.mddcore.usecases.subject.GetSubjectUseCase;
import com.mddinfrastructure.response.SubjectResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Component
public class SubjectController implements SubjectResource{

    private final UseCaseExecutor useCaseExecutor;
    private final GetSubjectUseCase getSubjectUseCase;
    private final GetAllSubjectUseCase getAllSubjectUseCase;

    public SubjectController(UseCaseExecutor useCaseExecutor, GetSubjectUseCase getSubjectUseCase, GetAllSubjectUseCase getAllSubjectUseCase) {
        this.useCaseExecutor = useCaseExecutor;
        this.getSubjectUseCase = getSubjectUseCase;
        this.getAllSubjectUseCase = getAllSubjectUseCase;
    }

    @Override
    public CompletableFuture<List<SubjectResponse>> getAllSubject() {
        return useCaseExecutor.execute(
                getAllSubjectUseCase,
                new GetAllSubjectUseCase.InputValues(),
                outputValues -> SubjectResponse.from(outputValues.subjectList())
        );
    }

    @Override
    public CompletableFuture<SubjectResponse> getSubjectById(@PathVariable Long id) {
        return useCaseExecutor.execute(
            getSubjectUseCase,
            new GetSubjectUseCase.InputValues(id),
            outputValues -> SubjectResponse.from(outputValues.subject())
        );
    }
}
