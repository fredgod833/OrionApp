package com.mdddetails.repository;


import com.mddcore.domain.repository.IUserRepository;
import com.mdddetails.mapper.UserMapperDetails;
import com.mdddetails.models.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.stream.Collectors;

@Repository
public class UserRepositoryImpl implements IUserRepository {

    @Autowired
    private UserJpaRepository jpaUserRepository;

    @Autowired
    private UserMapperDetails userMapperDetails;

    public UserRepositoryImpl(UserJpaRepository jpaUserRepository, UserMapperDetails userMapperDetails) {
        this.jpaUserRepository = jpaUserRepository;
        this.userMapperDetails = userMapperDetails;
    }

    @Override
    public List<User> findAll() {
        return jpaUserRepository.findAll().stream().map(userMapperDetails::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<User> findById(Long id) {
        return jpaUserRepository.findById(id).map(userMapperDetails::toDto);
    }

    @Override
    public User save(User user) {
        UserEntity userEntity = userMapperDetails.toEntity(user);
        UserEntity savedEntity = jpaUserRepository.save(userEntity);
        return userMapperDetails.toDto(savedEntity);
    }

    @Override
    public void delete(User user) {
        UserEntity userEntity = userMapperDetails.toEntity(user);
        jpaUserRepository.delete(userEntity);
    }

    @Override
    public Boolean existByEmail(String email) {
        return jpaUserRepository.existsByEmail(email);
    }

}
