package com.mddcore.usecases.subject;

import com.mddcore.domain.models.Subject;
import com.mddcore.usecases.EntityMapper;
import com.mddcore.usecases.subject.dto.SubjectDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SubjectMapper extends EntityMapper<Subject, SubjectDto> {
}
