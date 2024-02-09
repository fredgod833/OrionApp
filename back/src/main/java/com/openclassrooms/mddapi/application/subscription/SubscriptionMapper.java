package com.openclassrooms.mddapi.application.subscription;

import com.openclassrooms.mddapi.application.EntityMapper;
import com.openclassrooms.mddapi.application.subscription.dto.SubscriptionDto;
import com.openclassrooms.mddapi.domain.subscription.Subscription;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SubscriptionMapper extends EntityMapper<Subscription, SubscriptionDto> {
}
