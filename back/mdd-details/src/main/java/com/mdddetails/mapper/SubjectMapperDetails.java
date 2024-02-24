package com.mdddetails.mapper;

import com.mddcore.domain.models.Subject;
import com.mdddetails.models.SubjectEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {IdConverter.class})
public interface SubjectMapperDetails extends EntMapper<Subject, SubjectEntity> {

    @Override
    @Mapping(source = "id", target = "id", qualifiedByName = "LongToIdentity")
    Subject toDomain(SubjectEntity entity);

    @Override
    @Mapping(source = "id", target = "id", qualifiedByName = "IdentityToLong")
    SubjectEntity toEntity(Subject entity);
}
