package com.mddcore.usecases.ut.subject;

import com.mddcore.domain.models.Subject;
import com.mddcore.domain.repository.ISubjectRepository;
import com.mddcore.usecases.subject.GetSubjectUseCase;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class GetSubjectUseCaseUnitTest {
    @InjectMocks
    private GetSubjectUseCase useCase;
    @Mock
    private ISubjectRepository repository;

    @Test
    public void GetSubject_ShouldReturnSubject_WhenSubjectFoundInDb() {
        Long subjectId = 1L;
        Subject subject = new Subject();
        subject.setDescription("subject unit test");

        doReturn(Optional.of(subject)).when(repository).findById(1L);

        GetSubjectUseCase.InputValues inputValues = new GetSubjectUseCase.InputValues(subjectId);
        GetSubjectUseCase.OutputValues outputValues = useCase.execute(inputValues);

        assertThat(outputValues.subject().getDescription()).isEqualTo(subject.getDescription());
        verify(repository, times(1)).findById(subjectId);
    }

    @Test
    public void GetSubject_ShouldThrowError_WhenSubjectNotFoundInDb() {
        Long subjectId = 1L;

        doThrow(new IllegalArgumentException("Subject not found with id : " + subjectId)).when(repository).findById(subjectId);

        GetSubjectUseCase.InputValues inputValues = new GetSubjectUseCase.InputValues(subjectId);

        assertThatThrownBy(() -> useCase.execute(inputValues))
                .isInstanceOf(IllegalArgumentException.class)
                        .hasMessageContaining("Subject not found with id : " + subjectId);

        verify(repository, times(1)).findById(subjectId);
    }
}
