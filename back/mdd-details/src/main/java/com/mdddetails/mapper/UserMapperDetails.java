package com.mdddetails.mapper;

import com.mddcore.domain.models.User;
import com.mdddetails.models.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {IdConverter.class, SubscriptionMapperDetails.class})
public interface UserMapperDetails extends EntMapper<User, UserEntity> {

    @Override
    @Mapping(source = "id", target = "id", qualifiedByName = "LongToIdentity")
    @Mapping(source = "subscriptionEntities", target = "subscriptionList")
    User toDomain(UserEntity entity);

    @Override
    @Mapping(source = "id", target = "id", qualifiedByName = "IdentityToLong")
    @Mapping(source = "subscriptionList", target = "subscriptionEntities")
    UserEntity toEntity(User entity);
}