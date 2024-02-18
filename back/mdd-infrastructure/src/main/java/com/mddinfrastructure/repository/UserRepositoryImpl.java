package com.mddinfrastructure.repository;

import com.mddcore.domain.models.User;
import com.mddcore.domain.repository.IUserRepository;

import java.util.List;
import java.util.Optional;

public class UserRepositoryImpl implements IUserRepository {

    private final UserRepoApi userRepoApi;

    public UserRepositoryImpl(UserRepoApi userRepoApi) {
        this.userRepoApi = userRepoApi;
    }

    @Override
    public Boolean existByEmail(String email) {
        return userRepoApi.existByEmail(email);
    }

    @Override
    public Optional<User> findById(Long id) {
        return userRepoApi.findById(id);
    }
    public List<User> findAll() {
        return userRepoApi.findAll();
    }
    public  save(User entity) {
        return userRepoApi.save(entity);
    }

    public void delete(User entity) {

    }
}
