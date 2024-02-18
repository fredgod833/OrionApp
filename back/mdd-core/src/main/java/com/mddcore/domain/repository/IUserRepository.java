package com.mddcore.domain.repository;

import com.mddcore.domain.models.User;

public interface IUserRepository extends IRepository<User> {
    public Boolean existByEmail(String email);
}
