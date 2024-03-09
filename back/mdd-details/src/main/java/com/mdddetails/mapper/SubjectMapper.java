package com.mdddetails.mapper;

import com.mddcore.domain.models.Subject;
import com.mdddetails.models.SubjectEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {SubscriptionMapper.class, ArticleMapper.class})
public interface SubjectMapper extends EntMapper<Subject, SubjectEntity> {
    @Mapping(target = "articleList", ignore = true)
    @Mapping(target = "subscriptionList", ignore = true)
    Subject toDomain(SubjectEntity entity);
}
