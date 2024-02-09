package com.openclassrooms.mddapi.application.subject;

import com.openclassrooms.mddapi.application.EntityMapper;
import com.openclassrooms.mddapi.application.subject.dto.SubjectDto;
import com.openclassrooms.mddapi.domain.subject.Subject;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SubjectMapper extends EntityMapper<Subject, SubjectDto> {
}
