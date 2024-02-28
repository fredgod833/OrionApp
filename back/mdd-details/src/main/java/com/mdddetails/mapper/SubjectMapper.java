package com.mdddetails.mapper;

import com.mddcore.domain.models.Subject;
import com.mdddetails.models.SubjectEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SubjectMapper extends EntMapper<Subject, SubjectEntity> {
}
