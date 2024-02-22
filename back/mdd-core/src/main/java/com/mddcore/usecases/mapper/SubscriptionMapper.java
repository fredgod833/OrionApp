package com.mddcore.usecases.mapper;

import com.mddcore.domain.models.Subscription;
import com.mddcore.usecases.dto.SubscriptionDto;
import org.mapstruct.Mapper;

@Mapper
public interface SubscriptionMapper extends EntityMapper<Subscription, SubscriptionDto> {
}
