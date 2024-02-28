package com.mdddetails.mapper;

import com.mddcore.domain.models.Subscription;
import com.mdddetails.models.SubscriptionEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SubscriptionMapper extends EntMapper<Subscription, SubscriptionEntity> {
}
