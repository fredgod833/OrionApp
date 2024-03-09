package com.mdddetails.mapper;

import com.mddcore.domain.models.Subscription;
import com.mdddetails.models.SubscriptionEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {UserDetailsMapper.class, SubjectMapper.class})
public interface SubscriptionMapper extends EntMapper<Subscription, SubscriptionEntity> {

    @Mapping(target = "userId", source = "user.id")
    Subscription toDomain(SubscriptionEntity entity);

    @Mapping(target = "user.id", source = "userId")
    SubscriptionEntity toEntity(Subscription domain);
}
