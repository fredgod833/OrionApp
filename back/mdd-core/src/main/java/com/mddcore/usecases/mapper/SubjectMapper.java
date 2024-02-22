package com.mddcore.usecases.mapper;

import com.mddcore.domain.models.Subject;
import com.mddcore.usecases.dto.SubjectDto;
import org.mapstruct.Mapper;

@Mapper
public interface SubjectMapper extends EntityMapper<Subject, SubjectDto> {
}
