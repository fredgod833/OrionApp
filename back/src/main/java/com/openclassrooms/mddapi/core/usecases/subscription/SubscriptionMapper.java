package com.openclassrooms.mddapi.core.usecases.subscription;

import com.openclassrooms.mddapi.core.usecases.EntityMapper;
import com.openclassrooms.mddapi.core.usecases.subscription.dto.SubscriptionDto;
import com.openclassrooms.mddapi.core.domain.models.Subscription;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SubscriptionMapper extends EntityMapper<Subscription, SubscriptionDto> {
}
