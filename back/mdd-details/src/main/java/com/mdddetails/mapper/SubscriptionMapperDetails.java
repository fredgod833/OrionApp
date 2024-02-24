package com.mdddetails.mapper;

import com.mddcore.domain.models.Subscription;
import com.mdddetails.models.SubscriptionEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = IdConverter.class)
public interface SubscriptionMapperDetails extends EntMapper<Subscription, SubscriptionEntity> {
    @Override
    @Mapping(source = "id", target = "id", qualifiedByName = "LongToIdentity")
    @Mapping(source = "user.id", target = "user.id", qualifiedByName = "LongToIdentity")
    @Mapping(source = "subject.id", target = "subject.id", qualifiedByName = "LongToIdentity")
    Subscription toDomain(SubscriptionEntity entity);

    @Override
    @Mapping(source = "id", target = "id", qualifiedByName = "IdentityToLong")
    @Mapping(source = "user.id", target = "user.id", qualifiedByName = "IdentityToLong")
    @Mapping(source = "subject.id", target = "subject.id", qualifiedByName = "IdentityToLong")
    SubscriptionEntity toEntity(Subscription entity);
}
