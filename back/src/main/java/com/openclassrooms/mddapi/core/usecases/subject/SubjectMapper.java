package com.openclassrooms.mddapi.core.usecases.subject;

import com.openclassrooms.mddapi.core.usecases.EntityMapper;
import com.openclassrooms.mddapi.core.usecases.subject.dto.SubjectDto;
import com.openclassrooms.mddapi.core.domain.models.Subject;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SubjectMapper extends EntityMapper<Subject, SubjectDto> {
}
