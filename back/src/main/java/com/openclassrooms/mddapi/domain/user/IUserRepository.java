package com.openclassrooms.mddapi.domain.user;

import com.openclassrooms.mddapi.domain.common.IRepository;

public interface IUserRepository extends IRepository<User> {
    public Boolean existByEmail(String email);
}
