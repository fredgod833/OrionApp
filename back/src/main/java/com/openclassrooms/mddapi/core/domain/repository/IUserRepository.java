package com.openclassrooms.mddapi.core.domain.repository;

import com.openclassrooms.mddapi.core.domain.models.User;

public interface IUserRepository extends IRepository<User> {
    public Boolean existByEmail(String email);
}
