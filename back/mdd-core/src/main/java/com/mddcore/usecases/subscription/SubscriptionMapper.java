package com.mddcore.usecases.subscription;

import com.mddcore.domain.models.Subscription;
import com.mddcore.usecases.EntityMapper;
import com.mddcore.usecases.subscription.dto.SubscriptionDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SubscriptionMapper extends EntityMapper<Subscription, SubscriptionDto> {
}
