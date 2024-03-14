package com.mddcore.usecases.ut.subject;

import com.mddcore.usecases.UseCaseExecutor;
import com.mddcore.usecases.subject.GetAllSubjectUseCase;
import com.mddcore.usecases.subject.GetSubjectUseCase;
import com.mddinfrastructure.response.SubjectResponse;
import com.mddinfrastructure.subject.SubjectController;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
public class SubjectControllerUnitTest {
    @InjectMocks
    private SubjectController subjectController;
    @Mock
    private UseCaseExecutor useCaseExecutor;
    @Mock
    private GetSubjectUseCase getSubjectUseCase;
    @Mock
    private GetAllSubjectUseCase getAllSubjectUseCase;

    @Test
    public void GetAllSubject_ShouldReturnListOfSubjectResponse() {
        SubjectResponse subjectResponse = new SubjectResponse(
                1L,
                "java",
                "java spring boot clean architecture"
        );
        List<SubjectResponse> subjectResponseList = new ArrayList<>();
        subjectResponseList.add(subjectResponse);

        doReturn(CompletableFuture.completedFuture(subjectResponseList))
                .when(useCaseExecutor)
                .execute(eq(getAllSubjectUseCase),
                        any(GetAllSubjectUseCase.InputValues.class),
                        any());

        List<SubjectResponse> resultSubjectList = subjectController.getAllSubject().join();

        assertThat(resultSubjectList).isEqualTo(subjectResponseList);
    }

    @Test
    public void GetSubjectById_ShouldReturnSubjectResponse() {
        SubjectResponse subjectResponse = new SubjectResponse(
                1L,
                "java",
                "java spring boot clean architecture"
        );

        doReturn(CompletableFuture.completedFuture(subjectResponse))
                .when(useCaseExecutor)
                .execute(eq(getSubjectUseCase),
                        any(GetSubjectUseCase.InputValues.class),
                        any());

        SubjectResponse resultSubjectList = subjectController.getSubjectById(1L).join();

        assertThat(resultSubjectList).isEqualTo(subjectResponse);
    }
}
