package com.mddcore.usecases.ut.subject;

import com.mddcore.domain.models.Subject;
import com.mddcore.domain.repository.ISubjectRepository;
import com.mddcore.usecases.subject.GetAllSubjectUseCase;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class GetAllSubjectUseCaseUnitTest {
    @InjectMocks
    private GetAllSubjectUseCase useCase;
    @Mock
    private ISubjectRepository repository;

    @Test
    public void GetAllSubject_ShouldReturnListOfSubject() {
        Subject subject = new Subject();
        subject.setDescription("List of subject unit test");

        List<Subject> subjectList = new ArrayList<>();
        subjectList.add(subject);

        doReturn(subjectList).when(repository).findAll();

        GetAllSubjectUseCase.InputValues inputValues = new GetAllSubjectUseCase.InputValues();
        GetAllSubjectUseCase.OutputValues outputValues = useCase.execute(inputValues);

        assertThat(outputValues.subjectList().get(0).getDescription()).isEqualTo(subject.getDescription());
        verify(repository, times(1)).findAll();
    }

}
