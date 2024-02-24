package com.mdddetails.mapper;

import com.mddcore.domain.models.Identity;
import org.mapstruct.Named;

public final class IdConverter {

    @Named("IdentityToLong")
    public static Long identityToLong(Identity id) {
        return id != null ? id.getNumber() : null;
    }

    @Named("LongToIdentity")
    public static Identity longToIdentity(Long id) {
        return id != null ? new Identity(id) : null;
    }
}
