package com.mdddetails.ut;

import com.mddcore.domain.models.Subject;
import com.mdddetails.mapper.SubjectMapper;
import com.mdddetails.models.SubjectEntity;
import com.mdddetails.repository.subject.SubjectJpaRepo;
import com.mdddetails.repository.subject.SubjectRepoImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SubjectRepoImplUnitTest {
    @InjectMocks
    private SubjectRepoImpl subjectRepo;
    @Mock
    private SubjectJpaRepo jpaRepo;
    @Mock
    private SubjectMapper subjectMapper;

    @Test
    public void findAll_ShouldReturnListOfSubjects_WhenSubjectsExist() {

        SubjectEntity subjectEntity1 = new SubjectEntity();
        subjectEntity1.setDescription("unit test");
        SubjectEntity subjectEntity2 = new SubjectEntity();
        subjectEntity2.setDescription("unit test subject 2");
        Subject subject1 = new Subject();
        subject1.setDescription("unit test");
        Subject subject2 = new Subject();
        subject2.setDescription("unit test subject 2");

        when(jpaRepo.findAll()).thenReturn(List.of(subjectEntity1, subjectEntity2));
        when(subjectMapper.toDomain(subjectEntity1)).thenReturn(subject1);
        when(subjectMapper.toDomain(subjectEntity2)).thenReturn(subject2);

        List<Subject> subjects = subjectRepo.findAll();

        assertThat(subjects.get(0).getDescription()).isEqualTo(subjectEntity1.getDescription());
        assertThat(subjects.get(1).getDescription()).isEqualTo(subjectEntity2.getDescription());
        verify(jpaRepo).findAll();
        verify(subjectMapper, times(2)).toDomain(any(SubjectEntity.class));
    }

    @Test
    public void findById_ShouldReturnSubject_WhenSubjectExists() {
        Long id = 1L;
        SubjectEntity subjectEntity = new SubjectEntity();
        subjectEntity.setDescription("unit test");
        Subject subject = new Subject();
        subject.setDescription("unit test");

        when(jpaRepo.findById(id)).thenReturn(Optional.of(subjectEntity));
        when(subjectMapper.toDomain(subjectEntity)).thenReturn(subject);

        Optional<Subject> result = subjectRepo.findById(id);

        assertThat(result.get().getDescription()).isEqualTo(subjectEntity.getDescription());
        verify(jpaRepo).findById(id);
        verify(subjectMapper).toDomain(subjectEntity);
    }
}
